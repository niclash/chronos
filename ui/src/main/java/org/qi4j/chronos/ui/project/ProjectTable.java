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
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
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
                public String getAccountId()
                {
                    return ProjectTable.this.getAccountId();
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, ProjectEntityComposite obj )
    {
        item.add( new SimpleLink( "name", obj.getName() )
        {
            public void linkClicked()
            {
                //TODO
            }
        } );

        item.add( new SimpleLink( "formalReference", obj.getReference() )
        {
            public void linkClicked()
            {
                //TODO
            }
        } );

        item.add( new Label( "status", obj.getProjectStatus().toString() ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                //TODO
            }
        } );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "Status", "" );
    }

    public abstract String getAccountId();
}
