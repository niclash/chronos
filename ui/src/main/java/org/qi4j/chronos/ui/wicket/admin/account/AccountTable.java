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
package org.qi4j.chronos.ui.wicket.admin.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DefaultAction;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.util.ProjectUtil;
import org.qi4j.chronos.ui.wicket.admin.account.model.AccountDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.injection.scope.Uses;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class AccountTable extends ActionTable<IModel<Account>, String>
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = getLogger( AccountTable.class );

    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_FAIL = "deleteFailed";
    private static final String DISABLE_ACTION = "disableAction";
    private static final String DISABLE_SUCCESS = "disableSuccessful";
    private static final String DISABLE_FAIL = "disableFailed";
    private static final String ENABLE_ACTION = "enableAction";
    private static final String ENABLE_SUCCESS = "enableSuccessful";
    private static final String ENABLE_FAIL = "enableFailed";
    private static final String EDIT_LINK = "editLink";
    private static final String HEADER_NAME = "name";
    private static final String HEADER_REFERENCE = "formalReference";
    private static final String HEADER_ENABLED = "enabled";
    private static final String HEADER_PROJECT = "totalProject";
    private static final String HEADER_ACTIVE = "activeProject";
    private static final String HEADER_INACTIVE = "inactiveProject";
    private static final String HEADER_CLOSED = "closedProject";
    private static final String HEADER_LAST = "last";

    private AccountDataProvider dataProvider;

    public AccountTable( @Uses String aWicketId )
        throws IllegalArgumentException
    {
        super( aWicketId );

        addAction( new AccountDeleteAction() );
        addAction( new DisableAction() );
        addAction( new EnableAction() );
    }

    @Override
    public final AbstractSortableDataProvider<IModel<Account>, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new AccountDataProvider();
        }

        return dataProvider;
    }


    @Override
    public final void populateItems( Item item, final IModel iModel )
    {
        Account anAccount = (Account) iModel.getObject();
        item.add( createDetailPage( HEADER_NAME, anAccount.name().get(), iModel ) );
        item.add( createDetailPage( HEADER_REFERENCE, anAccount.reference().get(), iModel ) );

        item.add( new SimpleCheckBox( HEADER_ENABLED, anAccount.isEnabled().get(), true ) );

        Map<ProjectStatusEnum, Integer> projectStatusMap = ProjectUtil.getProjectStatusCount( anAccount );

        int totalProject = anAccount.projects().size();
        int totalActive = projectStatusMap.get( ProjectStatusEnum.ACTIVE );
        int totalInactive = projectStatusMap.get( ProjectStatusEnum.INACTIVE );
        int totalClosed = projectStatusMap.get( ProjectStatusEnum.CLOSED );

        item.add( new Label( HEADER_PROJECT, String.valueOf( totalProject ) ) );
        item.add( new Label( HEADER_ACTIVE, String.valueOf( totalActive ) ) );
        item.add( new Label( HEADER_INACTIVE, String.valueOf( totalInactive ) ) );
        item.add( new Label( HEADER_CLOSED, String.valueOf( totalClosed ) ) );

        item.add( new SimpleLink( EDIT_LINK, getString( EDIT_LINK ) )
        {
            public void linkClicked()
            {
                setResponsePage( newPage( AccountEditPage.class, getPageParameters( iModel ) ) );
            }
        } );
    }

    private SimpleLink createDetailPage( String id, String displayValue, final IModel iModel )
    {
        return new SimpleLink( id, displayValue )
        {
            public void linkClicked()
            {
                setResponsePage( newPage( AccountDetailPage.class, getPageParameters( iModel ) ) );
            }
        };
    }

    /**
     * Generate PageParameters to be used in Page constructor.
     *
     * @param iModel
     * @return
     */
    private PageParameters getPageParameters( final IModel iModel )
    {
        final PageParameters params = new PageParameters();
        Page page = this.getPage();
        Class<? extends Page> pageClass = page.getClass();
        String pageClassName = pageClass.getName();
        params.put( pageClassName, page );
        params.put( String.class.getName(), ( (Identity) iModel.getObject() ).identity().get() );

        return params;
    }

    @Override
    public final List<String> getTableHeaderList()
    {
        return getTableHeaderList(
            HEADER_NAME,
            HEADER_REFERENCE,
            HEADER_ENABLED,
            HEADER_PROJECT,
            HEADER_ACTIVE,
            HEADER_INACTIVE,
            HEADER_CLOSED,
            HEADER_LAST
        );
    }

    public List<String> getTableHeaderList( String... headers )
    {
        List<String> result = new ArrayList<String>();
        for( String header : headers )
        {
            result.add( getString( "tableHeader." + header ) );
        }

        return Collections.unmodifiableList( result );
    }

    private class AccountDeleteAction extends DeleteAction<IModel>
    {
        private static final long serialVersionUID = 1L;

        public AccountDeleteAction()
        {
            super( getString( DELETE_ACTION ) );
        }

        @Override
        public final void performAction( List<IModel> iModels )
        {
            final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
            try
            {
                for( IModel iModel : iModels )
                {
                    //                getAccountService().remove( (Account) iModel.getObject() );
                }
                ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            }
            catch( UnitOfWorkCompletionException uowce )
            {
                ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

                LOGGER.error( uowce.getLocalizedMessage(), uowce );
                error( getString( DELETE_FAIL ) );
            }
            info( getString( DELETE_SUCCESS ) );
        }
    }

    private class DisableAction extends DefaultAction<IModel>
    {
        private static final long serialVersionUID = 1L;

        public DisableAction()
        {
            super( getString( DISABLE_ACTION ) );
        }

        @Override
        public void performAction( List<IModel> iModels )
        {
            final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
            try
            {
                List<Account> accounts = new ArrayList<Account>();
                for( IModel iModel : iModels )
                {
                    accounts.add( (Account) iModel.getObject() );
                }
                //            getAccountService().enableAccounts( accounts, enable );
                ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            }
            catch( UnitOfWorkCompletionException uowce )
            {
                ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

                LOGGER.error( uowce.getLocalizedMessage(), uowce );
                error( getString( DISABLE_FAIL ) );
            }
            info( getString( DISABLE_SUCCESS ) );
        }
    }

    private class EnableAction extends DefaultAction<IModel>
    {
        private static final long serialVersionUID = 1L;

        public EnableAction()
        {
            super( getString( ENABLE_ACTION ) );
        }

        public void performAction( List<IModel> iModels )
        {
            final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
            try
            {
                List<Account> accounts = new ArrayList<Account>();
                for( IModel iModel : iModels )
                {
                    accounts.add( (Account) iModel.getObject() );
                }
                //            getAccountService().enableAccounts( accounts, enable );
                ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            }
            catch( UnitOfWorkCompletionException uowce )
            {
                ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

                LOGGER.error( uowce.getLocalizedMessage(), uowce );
                error( getString( ENABLE_FAIL ) );
            }
            info( getString( ENABLE_SUCCESS ) );
        }
    }
}
