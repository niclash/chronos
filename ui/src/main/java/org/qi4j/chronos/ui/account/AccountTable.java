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
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosWebApp;
import org.qi4j.chronos.ui.util.ProjectUtil;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
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
        UnitOfWork unitOfWork = getUnitOfWork();

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
            error( "Unable to delete selected account(s)!!!" );
        }
    }

    private void handleDisableAction( List<Account> accounts, boolean enabled )
    {
        // TODO kamil: use unitOfWork
        UnitOfWork unitOfWork = getUnitOfWork();

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
        return ChronosSession.get().getAccountService();
    }

    public void populateItems( Item item, final Account account )
    {
        item.add( createDetailPage( "name", account.name().get(), account ) );
        item.add( createDetailPage( "formalReference", account.reference().get(), account ) );

        item.add( new SimpleCheckBox( "enabled", account.isEnabled().get(), true ) );

        // TODO kamil: investigate
        Map<ProjectStatusEnum, Integer> projectStatusMap = ProjectUtil.getProjectStatusCount( account );

        int totalProject = account.projects().size();
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
                setResponsePage( newPage( AccountEditPage.class, getPageParameters( account ) ) );
            }
        } );
    }

    private SimpleLink createDetailPage( String id, String displayValue, final Account account )
    {
        return new SimpleLink( id, displayValue )
        {
            public void linkClicked()
            {
                setResponsePage( newPage( AccountDetailPage.class, getPageParameters( account ) ) );
            }
        };
    }

    private PageParameters getPageParameters( final Account account )
    {
        final PageParameters params = new PageParameters();
        params.put( this.getPage().getClass(), this.getPage() );
        params.put( Account.class, account );

        return params;
    }

    private UnitOfWork getUnitOfWork()
    {
        UnitOfWorkFactory unitOfWorkFactory = ChronosSession.get().getUnitOfWorkFactory();

        return null == unitOfWorkFactory.currentUnitOfWork() ? unitOfWorkFactory.newUnitOfWork() :
               unitOfWorkFactory.currentUnitOfWork();
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "Enabled", "Total Project", "Active", "Inactive", "Closed", "" );
    }

    public abstract Account getAccount();
}
