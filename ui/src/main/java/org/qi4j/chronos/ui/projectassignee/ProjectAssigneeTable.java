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
package org.qi4j.chronos.ui.projectassignee;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public abstract class ProjectAssigneeTable extends ActionTable<ProjectAssigneeEntityComposite, String>
{
    private ProjectAssigneeDataProvider dataProvider;

    public ProjectAssigneeTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<ProjectAssigneeEntityComposite, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new ProjectAssigneeDataProvider()
            {
                public ProjectEntityComposite getProject()
                {
                    return ProjectAssigneeTable.this.getProject();
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, ProjectAssigneeEntityComposite obj )
    {

        final String projectAssigneeId = obj.getIdentity();

        item.add( new Label( "firstName", obj.getStaff().getFirstName() ) );
        item.add( new Label( "lastName", obj.getStaff().getLastName() ) );
        CheckBox isLeadCheckBox = new CheckBox( "isLead", new Model( obj.isLead() ) );

        isLeadCheckBox.setEnabled( false );

        item.add( isLeadCheckBox );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ProjectAssigneeEditPage editPage = new ProjectAssigneeEditPage( (BasePage) this.getPage() )
                {
                    public ProjectAssigneeEntityComposite getProjectAssignee()
                    {
                        return ChronosWebApp.getServices().getProjectAssigneeService().get( projectAssigneeId );
                    }

                    public PriceRateScheduleComposite getPriceRateSchedule()
                    {
                        return getProject().getPriceRateSchedule();
                    }

                    public ProjectEntityComposite getProject()
                    {
                        return ProjectAssigneeTable.this.getProject();
                    }
                };

                setResponsePage( editPage );
            }
        } );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "First Name", "Last name", "Is Lead", "" );
    }

    public abstract ProjectEntityComposite getProject();
}
