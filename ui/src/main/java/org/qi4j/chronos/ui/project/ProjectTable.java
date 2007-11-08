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
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;

public abstract class ProjectTable extends ActionTable<ProjectEntityComposite, String>
{
    private ProjectDataProvider provider;

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
        addAction( new SimpleDeleteAction<ProjectEntityComposite>( "Delete" )
        {
            public void performAction( List<ProjectEntityComposite> projects )
            {
                getProjectService().delete( projects );

                info( "Selected project(s) are deleted." );
            }
        } );

        addAction( new SimpleAction<ProjectEntityComposite>( "Change to active status" )
        {
            public void performAction( List<ProjectEntityComposite> projects )
            {
                getProjectService().changeProjectStatus( ProjectStatus.ACTIVE, projects );

                info( "Selected project(s) are changed to status Active" );
            }
        } );

        addAction( new SimpleAction<ProjectEntityComposite>( "Change to inactive status" )
        {
            public void performAction( List<ProjectEntityComposite> projects )
            {
                getProjectService().changeProjectStatus( ProjectStatus.INACTIVE, projects );

                info( "Selected project(s) are changed to inactive status" );
            }
        } );

        addAction( new SimpleAction<ProjectEntityComposite>( "Change to closed status " )
        {
            public void performAction( List<ProjectEntityComposite> projects )
            {
                getProjectService().changeProjectStatus( ProjectStatus.CLOSED, projects );

                info( "Selected project(s) are changed to  Closed ActivationStatus" );
            }
        } );
    }

    public AbstractSortableDataProvider<ProjectEntityComposite, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new ProjectDataProvider()
            {
                public int getSize()
                {
                    return ProjectTable.this.getSize();
                }

                public List<ProjectEntityComposite> dataList( int first, int count )
                {
                    return ProjectTable.this.dataList( first, count );
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, ProjectEntityComposite obj )
    {
        final String projectId = obj.getIdentity();

        item.add( createDetailLink( "name", obj.getName(), projectId ) );
        item.add( createDetailLink( "formalReference", obj.getReference(), projectId ) );

        item.add( new Label( "status", obj.getProjectStatus().toString() ) );

        SimpleLink editLink = createEditLink( projectId );

        item.add( editLink );
    }

    private SimpleLink createEditLink( final String projectId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ProjectEditPage editPage = new ProjectEditPage( (BasePage) this.getPage() )
                {
                    public ProjectEntityComposite getProject()
                    {
                        return getProjectService().get( projectId );
                    }
                };

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
                    public ProjectEntityComposite getProject()
                    {
                        return getProjectService().get( projectId );
                    }
                };

                setResponsePage( detailPage );
            }
        };
    }

    private ProjectService getProjectService()
    {
        return ChronosWebApp.getServices().getProjectService();
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "ActivationStatus", "" );
    }

    public abstract int getSize();

    public abstract List<ProjectEntityComposite> dataList( int first, int count );
}
