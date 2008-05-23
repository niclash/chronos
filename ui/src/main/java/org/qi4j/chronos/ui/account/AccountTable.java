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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.util.ProjectUtil;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountTable extends ActionTable<IModel, String>
{
    private final static Logger LOGGER = LoggerFactory.getLogger( AccountTable.class );
    private AbstractSortableDataProvider<IModel, String> dataProvider;
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

    public AccountTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new SimpleDeleteAction<IModel>( getString( DELETE_ACTION ) )
            {
                public void performAction( List<IModel> iModels )
                {
                    handleDeleteAction( iModels );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );

        addAction(
            new SimpleAction<IModel>( getString( DISABLE_ACTION ) )
            {
                public void performAction( List<IModel> iModels )
                {
                    handleEnableAction( iModels, false );
                    info( getString( DISABLE_SUCCESS ) );
                }
            }
        );

        addAction(
            new SimpleAction<IModel>( getString( ENABLE_ACTION ) )
            {
                public void performAction( List<IModel> iModels )
                {
                    handleEnableAction( iModels, true );
                    info( getString( ENABLE_SUCCESS ) );
                }
            }
        );
    }

    private void handleDeleteAction( List<IModel> iModels )
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
    }

    private void handleEnableAction( List<IModel> iModels, boolean enable )
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
            error( getString( enable ? ENABLE_FAIL : DISABLE_FAIL ) );
        }
    }

    public AbstractSortableDataProvider<IModel, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new AbstractSortableDataProvider<IModel, String>()
            {
                public int getSize()
                {
//                    return getAccountService().count();
                    return 0;
                }

                public String getId( IModel t )
                {
//                    return getAccountService().getId( (Account) t.getObject() );
                    return null;
                }

                public IModel load( final String s )
                {
                    return new CompoundPropertyModel(
                        new LoadableDetachableModel()
                        {
                            public Object load()
                            {
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, AccountEntityComposite.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> accounts = new ArrayList<IModel>();
                    for( final String accountId : getAccountIds() )
                    {
                        accounts.add(
                            new CompoundPropertyModel(
                                new LoadableDetachableModel()
                                {
                                    public Object load()
                                    {
                                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( accountId, AccountEntityComposite.class );
                                    }
                                }
                            )
                        );
                    }

                    return accounts.subList( first, first + count );
                }
            };
        }
        return dataProvider;
    }

/*
    private static AccountService getAccountService()
    {
        return ChronosSession.get().getAccountService();
    }
*/

    private static List<String> getAccountIds()
    {
        List<String> list = new ArrayList<String>();
/*
        for( Account account : getAccountService().findAll() )
        {
            list.add( ( (Identity) account).identity().get() );
        }
*/

        return list;
    }

    public void populateItems( Item item, final IModel iModel )
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
        params.put( this.getPage().getClass(), this.getPage() );
        params.put( String.class, ( (Identity) iModel.getObject() ).identity().get() );

        return params;
    }

    public List<String> getTableHeaderList()
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

    public List<String> getTableHeaderList( String...headers )
    {
        List<String> result = new ArrayList<String>();
        for( String header : headers )
        {
            result.add( getString( "tableHeader." + header ) );
        }

        return Collections.unmodifiableList( result );
    }
}
