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
import java.util.Date;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.contactperson.ContactPersonDataProvider;
import org.qi4j.chronos.ui.contactperson.ContactPersonTable;
import org.qi4j.chronos.ui.legalcondition.LegalConditionTab;
import org.qi4j.chronos.ui.pricerate.PriceRateTab;
import org.qi4j.chronos.ui.projectassignee.ProjectAssigneeTab;
import org.qi4j.chronos.ui.task.TaskTab;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;
import org.qi4j.chronos.ui.workentry.WorkEntryDataProvider;
import org.qi4j.chronos.ui.workentry.WorkEntryTab;

public final class ProjectDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private Page returnPage;

    public ProjectDetailPage( Page returnPage, IModel<Project> projectModel )
    {
        this.returnPage = returnPage;

        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ProjectDetailForm( "projectDetailForm", projectModel ) );
    }

    private class ProjectDetailForm extends Form<Project>
    {
        private static final long serialVersionUID = 1L;

        public ProjectDetailForm( String id, IModel<Project> projectModel )
        {
            super( id );

            ChronosCompoundPropertyModel<Project> model = new ChronosCompoundPropertyModel<Project>( projectModel.getObject() );
            setModel( model );

            TextField<String> projectNameField =
                new TextField<String>( "projectNameField", model.<String>bind( "name" ) );
            TextField<String> formalReferenceField =
                new TextField<String>( "formalReferenceField", model.<String>bind( "reference" ) );
            TextField<String> projectStatusField =
                new TextField<String>( "projectStatusField", model.<String>bind( "projectStatus" ) );
            TextField<String> projectOwnerField =
                new TextField<String>( "customerField", model.<String>bind( "customer.name" ) );
            TextField<String> primaryContactField =
                new TextField<String>( "primaryContactField", model.<String>bind( "customer.name" ) );
            TextField<Date> estimateStartTimeField =
                new TextField<Date>( "estimateStartTimeField", model.<Date>bind( "estimateTime.startTime" ) );
            TextField<Date> estimateEndTimeField =
                new TextField<Date>( "estimateEndTimeField", model.<Date>bind( "estimateTime.endTime" ) );
            TextField<Date> actualStartTimeField =
                new TextField<Date>( "actualStartTimeField", model.<Date>bind( "actualTime.startTime" ) );
            TextField<Date> actualEndTimeField =
                new TextField<Date>( "actualEndTimeField", model.<Date>bind( "actualTime.endTime" ) );

            final WebMarkupContainer actualTimeContainer = new WebMarkupContainer( "actualTimeContainer" );

            actualTimeContainer.add( actualStartTimeField );
            actualTimeContainer.add( actualEndTimeField );

            final Button submitButton =
                new Button( "submitButton", new Model<String>( "Return" ) )
                {
                    private static final long serialVersionUID = -3931151764245902532L;

                    public void onSubmit()
                    {
                        setResponsePage( returnPage );
                    }
                };

            final List<ITab> tabs = new ArrayList<ITab>();

//            tabs.add( createContactPersonTab() );
            tabs.add( createPriceRateTab() );
            tabs.add( createProjectAssigneeTab() );
            tabs.add( createTaskTab() );
            tabs.add( createWorkEntryTab() );
            tabs.add( createLegalConditionTab() );

            final TabbedPanel tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            Customer customer = getModelObject().customer().get();
            ChronosDetachableModel<Customer> customerModel = new ChronosDetachableModel<Customer>( customer );

            final ContactPersonTable contactPersonTable =
                new ContactPersonTable( "contactPersonTable", customerModel
                    , new ContactPersonDataProvider( customerModel ) );

            contactPersonTable.setActionBarVisible( false );
            contactPersonTable.setNavigatorVisible( false );

            add( projectNameField );
            add( formalReferenceField );
            add( projectStatusField );
            add( projectOwnerField );
            add( primaryContactField );
            add( estimateStartTimeField );
            add( estimateEndTimeField );
            add( actualTimeContainer );
            add( submitButton );
            add( tabbedPanel );
            add( contactPersonTable );

            if( projectModel.getObject().projectStatus().get() != ProjectStatusEnum.CLOSED )
            {
                actualTimeContainer.setVisible( false );
            }
        }

        @SuppressWarnings( "unchecked")
        private WorkEntryTab createWorkEntryTab()
        {
            ChronosDetachableModel<Project> project = new ChronosDetachableModel<Project>( getModelObject() );

            ProjectAssignee projectAssignee = getProjectAssignee();

            IModel<ProjectAssignee> projectAssigneeIModel;

            if(projectAssignee!=null)
            {
                projectAssigneeIModel = new ChronosDetachableModel<ProjectAssignee>( projectAssignee);
            }else{
                projectAssigneeIModel= new Model();
            }

            return new WorkEntryTab( "WorkEntries", project, new WorkEntryDataProvider( project ), projectAssigneeIModel );
        }

        private ProjectAssignee getProjectAssignee()
        {
            AuthenticatedWebSession authenticatedSession = (AuthenticatedWebSession) getSession();
            Roles roles = authenticatedSession.getRoles();
            
            //TODO bp. This may return null if current user is Customer or ACCOUNT_ADMIN.
            //TODO got better way to handle this?
            if( roles.contains( SystemRole.STAFF ) )
            {
                Staff staff = (Staff) getChronosSession().getUser();
                for( ProjectAssignee projectAssignee : getModelObject().projectAssignees() )
                {
                    if( staff.equals( projectAssignee.staff().get() ) )
                    {
                        return projectAssignee;
                    }
                }

                return null;
            }

            return null;
        }

        private LegalConditionTab createLegalConditionTab()
        {
            return new LegalConditionTab( "Legal Condition", getModel() );
        }

        private TaskTab createTaskTab()
        {
            return new TaskTab( "Task", getModel() );
        }

        private ProjectAssigneeTab createProjectAssigneeTab()
        {
            return new ProjectAssigneeTab( "Project Assignee", getModel() );
        }

        private PriceRateTab createPriceRateTab()
        {
            ChronosDetachableModel<PriceRateSchedule> priceRateSchedule =
                new ChronosDetachableModel<PriceRateSchedule>( getModel().getObject().priceRateSchedule().get() );

            return new PriceRateTab( "Price Rate Schedule", priceRateSchedule );
        }
    }
}
