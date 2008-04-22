/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.account;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.Session;
import org.apache.wicket.PageParameters;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.util.ProjectUtil;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;

public abstract class AccountTable extends ActionTable<Account, String>
{
    private AccountDataProvider dataProvider;

    public AccountTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<Account>( "Delete account" )
        {
            public void performAction( List<Account> accounts )
            {
                handleDeleteAction( accounts );

                info( "Selected account(s) are deleted." );
            }
        } );

        addAction( new SimpleAction<Account>( "Disable account" )
        {
            public void performAction( List<Account> accounts )
            {
                handleDisableAction( accounts, false );

                info( "Selected account(s) are disabled." );
            }
        } );

        addAction( new SimpleAction<Account>( "Enable account" )
        {
            public void performAction( List<Account> accounts )
            {
                handleDisableAction( accounts, true );

                info( "Selected account(s) are enabled." );
            }
        } );
    }

    private void handleDeleteAction( List<Account> accounts )
    {
        // TODO kamil: use unitOfWork
        UnitOfWork unitOfWork = null == getUnitOfWorkFactory().currentUnitOfWork() ?
                                getUnitOfWorkFactory().newUnitOfWork() :
                                getUnitOfWorkFactory().currentUnitOfWork();

        getAccountService().removeAll( accounts );

        try
        {
            unitOfWork.complete();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            // TODO kamil: use LOGGER
            System.err.println( uowce.getLocalizedMessage() );
            uowce.printStackTrace();
            error( "Unable to delete selected accounts!!!" );
        }
    }

    private void handleDisableAction( List<Account> accounts, boolean enabled )
    {
        // TODO kamil: use unitOfWork
        UnitOfWork unitOfWork = null == getUnitOfWorkFactory().currentUnitOfWork() ?
                                getUnitOfWorkFactory().newUnitOfWork() :
                                getUnitOfWorkFactory().currentUnitOfWork();

        getAccountService().enableAccounts( accounts, enabled );

        try
        {
            unitOfWork.complete();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            // TODO kamil: use LOGGER
            System.err.println( uowce.getLocalizedMessage() );
            uowce.printStackTrace();
            error( "Unable to " + ( enabled ? "enable" : "disable" ) + " selected accounts!!!" );
        }
    }

    public AbstractSortableDataProvider<Account, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            // TODO kamil: migrate
//            dataProvider = new SimpleDataProvider<Account>()
//            {
//                public EntityService<Account> getEntityService()
//                {
//                    return AccountTable.this.getAccountService();
//                }
//            };
            dataProvider = new AccountDataProvider();
        }
        return dataProvider;
    }

    private AccountService getAccountService()
    {
        return ( ( ChronosSession ) Session.get() ).getAccountService();
    }

    private UnitOfWorkFactory getUnitOfWorkFactory()
    {
        return ( ( ChronosSession ) Session.get() ).getUnitOfWorkFactory();
    }

    public void populateItems( Item item, Account obj )
    {
        final String accountId = ( (Identity) obj).identity().get();

        item.add( createDetailPage( "name", obj.name().get(), accountId ) );
        item.add( createDetailPage( "formalReference", obj.reference().get(), accountId ) );

        item.add( new SimpleCheckBox( "enabled", obj.isEnabled().get(), true ) );

        // TODO kamil: investigate
        Map<ProjectStatusEnum, Integer> projectStatusMap = ProjectUtil.getProjectStatusCount( obj );

        int totalProject = obj.projects().size();
        int totalActive = projectStatusMap.get( ProjectStatusEnum.ACTIVE );
        int totalInactive = projectStatusMap.get( ProjectStatusEnum.INACTIVE );
        int totalClosed = projectStatusMap.get( ProjectStatusEnum.CLOSED );

        item.add( new Label( "totalProject", String.valueOf( totalProject ) ) );
        item.add( new Label( "activeProject", String.valueOf( totalActive ) ) );
        item.add( new Label( "inactiveProject", String.valueOf( totalInactive ) ) );
        item.add( new Label( "closedProject", String.valueOf( totalClosed ) ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                setResponsePage( newPage( AccountEditPage.class, getPageParameters( accountId ) ) );
            }
        } );
    }

    private SimpleLink createDetailPage( String id, String displayValue, final String accountId )
    {
        return new SimpleLink( id, displayValue )
        {
            public void linkClicked()
            {
                setResponsePage( newPage( AccountDetailPage.class, getPageParameters( accountId ) ) );
            }
        };
    }

    private PageParameters getPageParameters( final String accountId )
    {
        final PageParameters params = new PageParameters();
        params.put( this.getPage().getClass(), this.getPage() );
        params.put( String.class, accountId );

        return params;
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "Enabled", "Total Project", "Active", "Inactive", "Closed", "" );
    }

    public abstract Account getAccount();
}
