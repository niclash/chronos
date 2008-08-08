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

import java.util.List;
import java.util.Map;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.associations.HasAccounts;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DefaultAction;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.util.ProjectUtil;
import org.qi4j.chronos.ui.wicket.admin.account.model.AccountDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.injection.scope.Uses;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class AccountTable extends ActionTable<Account>
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


    private final static String[] COLUMN_NAMES = { HEADER_NAME, HEADER_REFERENCE, HEADER_ENABLED,
                                                   HEADER_PROJECT, HEADER_ACTIVE, HEADER_INACTIVE, HEADER_CLOSED, HEADER_LAST };

    public AccountTable( String aWicketId, IModel<? extends HasAccounts> hasAccounts, AccountDataProvider dataProvider )
        throws IllegalArgumentException
    {
        super( aWicketId, hasAccounts, dataProvider, COLUMN_NAMES );


        addAction( new AccountDeleteAction() );
        addAction( new DisableAction() );
        addAction( new EnableAction() );
    }

    private SimpleLink createDetailPage( String id, String displayValue, final String accountId )
    {
        return new SimpleLink( id, displayValue )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                setResponsePage( newPage( AccountDetailPage.class, getPageParameters( accountId ) ) );
            }
        };
    }

    /**
     * Generate PageParameters to be used in Page constructor.
     *
     * @param accountId
     * @return PageParameters
     */
    private PageParameters getPageParameters( final String accountId )
    {
        final PageParameters params = new PageParameters();
        Page page = this.getPage();
        Class<? extends Page> pageClass = page.getClass();
        String pageClassName = pageClass.getName();
        params.put( pageClassName, page );
        params.put( String.class.getName(), accountId );

        return params;
    }

    public void populateItems( Item<Account> item )
    {
        Account anAccount = item.getModelObject();
        final String accountId = anAccount.identity().get();

        item.add( createDetailPage( HEADER_NAME, anAccount.name().get(), accountId ) );
        item.add( createDetailPage( HEADER_REFERENCE, anAccount.reference().get(), accountId ) );

        item.add( new CheckBox( HEADER_ENABLED, new Model<Boolean>(anAccount.isEnabled().get())) );

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
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                setResponsePage( newPage( AccountEditPage.class, getPageParameters( accountId ) ) );
            }
        } );
    }

    private class AccountDeleteAction extends DeleteAction<Account>
    {
        private static final long serialVersionUID = 1L;

        public AccountDeleteAction()
        {
            super( getString( DELETE_ACTION ) );
        }

        @Override
        public final void performAction( List<Account> accounts )
        {
            final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
            try
            {
                for( Account account : accounts )
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

    private class DisableAction extends DefaultAction<Account>
    {
        private static final long serialVersionUID = 1L;

        public DisableAction()
        {
            super( getString( DISABLE_ACTION ) );
        }

        @Override
        public void performAction( List<Account> accounts )
        {
            final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
            try
            {
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

    private class EnableAction extends DefaultAction<Account>
    {
        private static final long serialVersionUID = 1L;

        public EnableAction()
        {
            super( getString( ENABLE_ACTION ) );
        }

        public void performAction( List<Account> accounts )
        {
            final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
            try
            {
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
