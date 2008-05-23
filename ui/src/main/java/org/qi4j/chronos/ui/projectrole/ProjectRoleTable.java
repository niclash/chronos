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
package org.qi4j.chronos.ui.projectrole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProjectRoleTable extends ActionTable<IModel, String>
{
    private AbstractSortableDataProvider<IModel, String> roleDataProvider;
    private static final Logger LOGGER = LoggerFactory.getLogger( ProjectRoleTable.class );
    private static final String DELETE_FAIL = "deleteFailed";
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";

    public ProjectRoleTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new SimpleDeleteAction<IModel>( getString( DELETE_ACTION ) )
            {
                public void performAction( List<IModel> projectRoles )
                {
                    handleDeleteAction( projectRoles );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
    }

    private void handleDeleteAction( List<IModel> projectRoles )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();

        try
        {
            final Account account = unitOfWork.dereference( getAccount() );
            for( IModel iModel : projectRoles )
            {
                final ProjectRole projectRole = (ProjectRole) iModel.getObject();
                account.projectRoles().remove( projectRole );
            }

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    public AbstractSortableDataProvider<IModel, String> getDetachableDataProvider()
    {
        if( roleDataProvider == null )
        {
            roleDataProvider = new AbstractSortableDataProvider<IModel, String>()
            {
                public int getSize()
                {
                    return ProjectRoleTable.this.getAccount().projectRoles().size();
                }

                public String getId( IModel t )
                {
                    return ( (Identity) t.getObject() ).identity().get();
                }

                public IModel load( final String s )
                {
                    return new CompoundPropertyModel(
                        new LoadableDetachableModel()
                        {
                            protected Object load()
                            {
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, ProjectRoleEntityComposite.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> models = new ArrayList<IModel>();
                    for( final String projectRoleId : ProjectRoleTable.this.dataList( first, count ) )
                    {
                        models.add(
                            new CompoundPropertyModel(
                                new LoadableDetachableModel()
                                {
                                    protected Object load()
                                    {
                                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( projectRoleId, ProjectRoleEntityComposite.class );
                                    }
                                }
                            )
                        );
                    }
                    return models;
                }
            };
        }

        return roleDataProvider;
    }

    public void populateItems( Item item, IModel iModel )
    {
        ProjectRole projectRole = (ProjectRole) iModel.getObject();
        final String projectRoleName = projectRole.name().get();
        final String projectId = ( (Identity) projectRole ).identity().get();

        item.add( new Label( "roleName", projectRoleName ) );

        SimpleLink editLink = createEditLink( projectId );
        item.add( editLink );
    }

    private SimpleLink createEditLink( final String projectRoleId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ProjectRoleEditPage roleEditPage = new ProjectRoleEditPage( getBasePage(), projectRoleId );

                setResponsePage( roleEditPage );
            }
        };
    }

    private BasePage getBasePage()
    {
        return (BasePage) this.getPage();
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "" );
    }

    public abstract Account getAccount();

    public abstract List<String> dataList( int first, int count );
}
