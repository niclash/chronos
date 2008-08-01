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

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasProjectAssignees;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;

public final class ProjectAssigneeTable extends ActionTable<ProjectAssignee>
{
    private static final long serialVersionUID = 1L;

    private final static String[] COLUMN_NAMES = { };

    public ProjectAssigneeTable( String id, IModel<? extends HasProjectAssignees> hasProjectAssignees, ProjectAssigneeDataProvider dataProvider )
    {
        super( id, hasProjectAssignees, dataProvider, COLUMN_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction( new DeleteAction<ProjectAssignee>( "Delete" )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<ProjectAssignee> projectAsssignees )
            {
                //TODO
//                getProjectAssigneeService().delete( projectAsssignees );

                info( "Selected project assignee(s) are deleted." );
            }
        } );
    }

    protected void authorizingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    public void populateItems( Item<ProjectAssignee> item )
    {
        final String projectAssigneeId = item.getModelObject().identity().get();

        Staff staff = item.getModelObject().staff().get();

        item.add( new Label( "firstName", staff.firstName().get() ) );
        item.add( new Label( "lastName", staff.lastName().get() ) );
        CheckBox isLeadCheckBox = new CheckBox( "isLead", new Model( item.getModelObject().isLead().get() ) );

        isLeadCheckBox.setEnabled( false );

        item.add( isLeadCheckBox );

        SimpleLink editLink = createEditLink( projectAssigneeId );

        item.add( editLink );
    }

    private SimpleLink createEditLink( final String projectAssigneeId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
//                ProjectAssigneeEditPage editPage = new ProjectAssigneeEditPage( (BasePage) this.getPage() )
//                {
//                    public ProjectAssignee getProjectAssignee()
//                    {
////                        return getProjectAssigneeService().get( projectAssigneeId );
//                        return null;
//                    }
//
//                    public PriceRateSchedule getPriceRateSchedule()
//                    {
//                        return getProject().priceRateSchedule().get();
//                    }
//
//                    public Project getProject()
//                    {
//                        return ProjectAssigneeTable.this.getProject();
//                    }
//                };
//
//                setResponsePage( editPage );
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }

}
