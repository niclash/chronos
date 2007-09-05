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
package org.qi4j.chronos.ui.projectowner;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public class ProjectOwnerTable extends ActionTable<ProjectOwnerEntityComposite>
{
    public ProjectOwnerTable( String id )
    {
        super( id );
    }

    private SimpleDataProvider<ProjectOwnerEntityComposite> provider;

    public AbstractSortableDataProvider<ProjectOwnerEntityComposite> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new SimpleDataProvider<ProjectOwnerEntityComposite>()
            {
                public EntityService<ProjectOwnerEntityComposite> getEntityService()
                {
                    return ChronosWebApp.getServices().getProjectOwnerService();
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, ProjectOwnerEntityComposite obj )
    {
        final String projectOwnerId = obj.getIdentity();

        add( createDetailLink( "name", obj.getName(), projectOwnerId ) );
        add( createDetailLink( "formalReference", obj.getReference(), projectOwnerId ) );

        add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ProjectOwnerEditPage editPage = new ProjectOwnerEditPage( (BasePage) this.getPage(), projectOwnerId );

                setResponsePage( editPage );
            }
        } );
    }

    private SimpleLink createDetailLink( String id, String displayValue, final String projectOwnerId )
    {
        return new SimpleLink( "id", displayValue )
        {
            public void linkClicked()
            {
                ProjectOwnerDetailPage detailPage = new ProjectOwnerDetailPage( projectOwnerId );

                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "" );
    }
}
