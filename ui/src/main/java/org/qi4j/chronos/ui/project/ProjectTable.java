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
package org.qi4j.chronos.ui.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProjectTable extends ActionTable<IModel, String>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ProjectTable.class );
    private AbstractSortableDataProvider<IModel, String> provider;
    private static final String DELETE_FAIL = "deleteFailed";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String UPDATE_FAIL = "updateFailed";

    public ProjectTable( String id )
    {
        super( id );

        addActions();
    }

    protected void authorizatiingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    private void addActions()
    {
        addAction(
            new SimpleDeleteAction<IModel>( "Delete" )
            {
                public void performAction( List<IModel> projects )
                {
                    handleDeleteAction( projects );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
        addAction(
            new SimpleAction<IModel>( "Change to active status" )
            {
                public void performAction( List<IModel> projects )
                {
                    handleStatusChangeAction( projects, ProjectStatusEnum.ACTIVE );
                    info( "Selected project(s) are changed to status Active" );
                }
            }
        );
        addAction(
            new SimpleAction<IModel>( "Change to inactive status" )
            {
                public void performAction( List<IModel> projects )
                {
                    handleStatusChangeAction( projects, ProjectStatusEnum.INACTIVE );
                    info( "Selected project(s) are changed to inactive status" );
                }
            }
        );

        addAction(
            new SimpleAction<IModel>( "Change to closed status " )
            {
                public void performAction( List<IModel> projects )
                {
                    handleStatusChangeAction( projects, ProjectStatusEnum.CLOSED );
                    info( "Selected project(s) are changed to  Closed ActivationStatus" );
                }
            }
        );
    }

    private void handleDeleteAction( List<IModel> projects )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();

        try
        {
            final HasProjects hasProjects = getHasProjectsModel().getObject();
            for( IModel iModel : projects )
            {
                final Project project = (Project) iModel.getObject();
                hasProjects.projects().remove( project );
                unitOfWork.remove( project );
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

    private void handleStatusChangeAction( List<IModel> projects, ProjectStatusEnum projectStatus )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            for( IModel iModel : projects )
            {
                final Project project = (Project) iModel.getObject();
                project.projectStatus().set( projectStatus );
            }
            unitOfWork.complete();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( UPDATE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    public AbstractSortableDataProvider<IModel, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new AbstractSortableDataProvider<IModel, String>()
            {
                public int getSize()
                {
                    return ProjectTable.this.getSize();
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
                            public Object load()
                            {
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, ProjectEntityComposite.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> models = new ArrayList<IModel>();
                    for( Project project : ProjectTable.this.dataList( first, count ) )
                    {
                        models.add( new ChronosCompoundPropertyModel<Project>( project ) );
                    }
                    return models;
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, IModel iModel )
    {
        final Project project = (Project) iModel.getObject();
        final String projectId = ( (Identity) project ).identity().get();

        item.add( createDetailLink( "name", project.name().get(), projectId ) );
        item.add( createDetailLink( "formalReference", project.reference().get(), projectId ) );
        item.add( new Label( "status", project.projectStatus().get().toString() ) );

        final SimpleLink editLink = createEditLink( iModel );
        item.add( editLink );
    }

    private SimpleLink createEditLink( final IModel iModel )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
//                ProjectEditPage editPage =
//                    new ProjectEditPage( (BasePage) this.getPage(), iModel );
//                setResponsePage( editPage );
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }

    private SimpleLink createDetailLink( String id, String text, final String projectId )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
                ProjectDetailPage detailPage = new ProjectDetailPage( this.getPage(), projectId );
                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "ActivationStatus", "" );
    }

    public abstract int getSize();

    public List<Project> dataList( int first, int count )
    {
        List<Project> projects = new ArrayList<Project>();
        for( Project project : ProjectTable.this.getHasProjectsModel().getObject().projects() )
        {
            projects.add( project );
        }
        return projects.subList( first, first + count );
    }

    public abstract IModel<HasProjects> getHasProjectsModel();
}
