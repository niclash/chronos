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

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;

public abstract class ProjectTable extends ActionTable<IModel, String>
{
    private AbstractSortableDataProvider<IModel, String> provider;

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
        addAction( new SimpleDeleteAction<Project>( "Delete" )
        {
            public void performAction( List<Project> projects )
            {
                // TODO kamil: use service
//                getProjectService().delete( projects );

                info( "Selected project(s) are deleted." );
            }
        } );

        addAction( new SimpleAction<Project>( "Change to active status" )
        {
            public void performAction( List<Project> projects )
            {
                // TODO kamil: use service
//                getProjectService().changeProjectStatus( ProjectStatusEnum.ACTIVE, projects );

                info( "Selected project(s) are changed to status Active" );
            }
        } );

        addAction( new SimpleAction<Project>( "Change to inactive status" )
        {
            public void performAction( List<Project> projects )
            {
                // TODO kamil: use service
//                getProjectService().changeProjectStatus( ProjectStatusEnum.INACTIVE, projects );

                info( "Selected project(s) are changed to inactive status" );
            }
        } );

        addAction( new SimpleAction<Project>( "Change to closed status " )
        {
            public void performAction( List<Project> projects )
            {
                // TODO kamil: use service
//                getProjectService().changeProjectStatus( ProjectStatusEnum.CLOSED, projects );

                info( "Selected project(s) are changed to  Closed ActivationStatus" );
            }
        } );
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
                    return ( (Identity) t.getObject()).identity().get();
                }

                public IModel load( final String s )
                {
                    return new CompoundPropertyModel(
                        new LoadableDetachableModel()
                        {
                            public Object load()
                            {
                                return getUnitOfWork().find( s, ProjectEntityComposite.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    return ProjectTable.this.dataList( first, count );
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, IModel iModel )
    {
        Project project = (Project) iModel.getObject();
        final String projectId = ( (Identity) project).identity().get();

        item.add( createDetailLink( "name", project.name().get(), projectId ) );
        item.add( createDetailLink( "formalReference", project.reference().get(), projectId ) );
        item.add( new Label( "status", project.projectStatus().get().toString() ) );

        SimpleLink editLink = createEditLink( projectId );
        item.add( editLink );
    }

    private SimpleLink createEditLink( final String projectId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                IModel iModel = new CompoundPropertyModel(
                    new LoadableDetachableModel()
                    {
                        public Object load()
                        {
                            return getUnitOfWork().find( projectId, ProjectEntityComposite.class );
                        }
                    }
                );
                ProjectEditPage editPage =
                    new ProjectEditPage( (BasePage) this.getPage(), iModel );
/*
                {
                    public org.qi4j.chronos.model.Project getProject()
                    {
                        for( Project project : getAccount().projects() )
                        {
                            if( projectId.equals( ( (Identity) project).identity().get() ) )
                            {
                                return project;
                            }
                        }

                        return null;
                        // TODO
//                        return getProjectService().get( projectId );
                    }
                };
*/

                setResponsePage( editPage );
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
                ProjectDetailPage detailPage = new ProjectDetailPage( this.getPage() )
                {
                    public Project getProject()
                    {
                        for( Project project : getAccount().projects() )
                        {
                            if( projectId.equals( ( (Identity) project).identity().get() ) )
                            {
                                return project;
                            }
                        }

                        return null;
                        // TODO
//                        return getProjectService().get( projectId );
                    }
                };

                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "ActivationStatus", "" );
    }

    protected UnitOfWork getUnitOfWork()
    {
        UnitOfWorkFactory factory = ChronosSession.get().getUnitOfWorkFactory();

        if( null == factory.currentUnitOfWork() || !factory.currentUnitOfWork().isOpen() )
        {
            return factory.newUnitOfWork();
        }
        else
        {
            return factory.currentUnitOfWork();
        }
    }

    public abstract int getSize();

    public abstract List<IModel> dataList( int first, int count );
}
