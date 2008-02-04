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
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.ProjectAssigneeService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;

public abstract class ProjectAssigneeTable extends ActionTable<ProjectAssigneeEntityComposite, String>
{
    private ProjectAssigneeDataProvider dataProvider;

    public ProjectAssigneeTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<ProjectAssigneeEntityComposite>( "Delete" )
        {
            public void performAction( List<ProjectAssigneeEntityComposite> projectAsssignees )
            {
                getProjectAssigneeService().delete( projectAsssignees );

                info( "Selected project assignee(s) are deleted." );
            }
        } );
    }

    protected void authorizatiingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
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
        final String projectAssigneeId = obj.identity().get();

        StaffEntityComposite staff = obj.staff().get();
        item.add( new Label( "firstName", staff.firstName().get() ) );
        item.add( new Label( "lastName", staff.lastName().get() ) );
        CheckBox isLeadCheckBox = new CheckBox( "isLead", new Model( obj.isLead().get() ) );

        isLeadCheckBox.setEnabled( false );

        item.add( isLeadCheckBox );

        SimpleLink editLink = createEditLink( projectAssigneeId );

        item.add( editLink );
    }

    private ProjectAssigneeService getProjectAssigneeService()
    {
        return ChronosWebApp.getServices().getProjectAssigneeService();
    }

    private SimpleLink createEditLink( final String projectAssigneeId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ProjectAssigneeEditPage editPage = new ProjectAssigneeEditPage( (BasePage) this.getPage() )
                {
                    public ProjectAssigneeEntityComposite getProjectAssignee()
                    {
                        return getProjectAssigneeService().get( projectAssigneeId );
                    }

                    public PriceRateScheduleComposite getPriceRateSchedule()
                    {
                        return getProject().priceRateSchedule().get();
                    }

                    public ProjectEntityComposite getProject()
                    {
                        return ProjectAssigneeTable.this.getProject();
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

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "First Name", "Last name", "Is Lead", "" );
    }

    public abstract ProjectEntityComposite getProject();
}
