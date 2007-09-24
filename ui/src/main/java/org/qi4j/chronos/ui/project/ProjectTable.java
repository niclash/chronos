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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public abstract class ProjectTable extends ActionTable<ProjectEntityComposite, String>
{
    private ProjectDataProvider provider;

    public ProjectTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<ProjectEntityComposite, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new ProjectDataProvider()
            {
                public AccountEntityComposite getAccount()
                {
                    return ProjectTable.this.getAccount();
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

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ProjectEditPage editPage = new ProjectEditPage( (BasePage) this.getPage() )
                {
                    public ProjectEntityComposite getProject()
                    {
                        return getProjectService().get( projectId );
                    }

                    public AccountEntityComposite getAccount()
                    {
                        return ProjectTable.this.getAccount();
                    }
                };

                setResponsePage( editPage );
            }
        } );
    }

    private SimpleLink createDetailLink( String id, String text, final String projectId )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
                ProjectDetailPage detailPage = new ProjectDetailPage( (BasePage) this.getPage() )
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
        return Arrays.asList( "Name", "Formal Reference", "Status", "" );
    }

    public abstract AccountEntityComposite getAccount();
}
