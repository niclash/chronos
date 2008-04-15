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
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.contactperson.ContactPersonTable;
import org.qi4j.chronos.ui.legalcondition.LegalConditionTab;
import org.qi4j.chronos.ui.pricerate.PriceRateTab;
import org.qi4j.chronos.ui.projectassignee.ProjectAssigneeTab;
import org.qi4j.chronos.ui.task.TaskTab;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.workentry.WorkEntryTab;
import org.qi4j.chronos.util.DateUtil;

public abstract class ProjectDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public ProjectDetailPage( Page returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ProjectDetailForm( "projectDetailForm" ) );
    }

    private class ProjectDetailForm extends Form
    {
        private Button submitButton;

        private SimpleTextField projectNameField;
        private SimpleTextField formalReferenceField;
        private SimpleTextField projectStatusField;
        private SimpleTextField projectOwnerField;
        private SimpleTextField primaryContactField;

        private SimpleTextField estimateStartTimeField;
        private SimpleTextField estimateEndTimeField;

        private SimpleTextField actualStartTimeField;
        private SimpleTextField actualEndTimeField;

        private WebMarkupContainer actualTimeContainer;

        private TabbedPanel tabbedPanel;

        private ContactPersonTable contactPersonTable;

        public ProjectDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            Project project = getProject();

            projectNameField = new SimpleTextField( "projectNameField", project.name().get() );
            formalReferenceField = new SimpleTextField( "formalReferenceField", project.reference().get() );

            ProjectStatusEnum projectStatus = project.projectStatus().get();
            projectStatusField = new SimpleTextField( "projectStatusField", projectStatus.toString() );

            Customer projectCustomer = project.customer().get();
            projectOwnerField = new SimpleTextField( "customerField", projectCustomer.name().get() );

            String primaryContact = project.primaryContactPerson().get().fullName().get();
            primaryContactField = new SimpleTextField( "primaryContactField", primaryContact );

            TimeRange projectEstimateTime = project.estimateTime().get();
            estimateStartTimeField = new SimpleTextField( "estimateStartTimeField", DateUtil.formatDate( projectEstimateTime.startTime().get() ) );
            estimateEndTimeField = new SimpleTextField( "estimateEndTimeField", DateUtil.formatDate( projectEstimateTime.endTime().get() ) );

            TimeRange projectActualTime = project.actualTime().get();
            String actualStartTime = projectActualTime.startTime().get() == null ? "" : DateUtil.formatDate( projectActualTime.startTime().get() );
            String actualEndTime = projectActualTime.endTime().get() == null ? "" : DateUtil.formatDate( projectActualTime.endTime().get() );

            actualStartTimeField = new SimpleTextField( "actualStartTimeField", actualStartTime );
            actualEndTimeField = new SimpleTextField( "actualEndTimeField", actualEndTime );

            actualTimeContainer = new WebMarkupContainer( "actualTimeContainer" );

            actualTimeContainer.add( actualStartTimeField );
            actualTimeContainer.add( actualEndTimeField );

            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            List<AbstractTab> tabs = new ArrayList<AbstractTab>();

//            tabs.add( createContactPersonTab() );
            tabs.add( createPriceRateTab() );
            tabs.add( createProjectAssigneeTab() );
            tabs.add( createTaskTab() );
            tabs.add( createWorkEntryTab() );
            tabs.add( createLegalConditionTab() );

            tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            contactPersonTable = new ContactPersonTable( "contactPersonTable" )
            {
                public HasContactPersons getHasContactPersons()
                {
                    return getProject();
                }

                public Customer getCustomer()
                {
                    return getProject().customer().get();
                }
            };

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

            if( projectStatus != ProjectStatusEnum.CLOSED )
            {
                actualTimeContainer.setVisible( false );
            }
        }
    }

    private WorkEntryTab createWorkEntryTab()
    {
        return new WorkEntryTab( "WorkEntries" )
        {
            public void addingWorkEntry( WorkEntry workEntry )
            {
                ProjectDetailPage.this.addingWorkEntry( workEntry );
            }

            public ProjectAssignee getProjectAssignee()
            {
                return ProjectDetailPage.this.getProjectAssignee();
            }

            public HasWorkEntries getHasWorkEntries()
            {
                return getProject();
            }
        };
    }

    private void addingWorkEntry( WorkEntry workEntry )
    {
        Project project = getProject();

        //add workEntry to project
        project.workEntries().add( workEntry );

        //update project
        // TODO migrate
//        getServices().getProjectService().update( project );
    }

    private ProjectAssignee getProjectAssignee()
    {
        //TODO bp. This may return null if current user is Customer or ACCOUNT_ADMIN.
        //TODO got better way to handle this?
        if( getChronosSession().isStaff() )
        {
            Staff staff = (Staff) getChronosSession().getUser();

            ProjectAssignee projectAssignee = getServices().getProjectAssigneeService().getProjectAssignee( getProject(), staff );

            return projectAssignee;
        }

        return null;
    }

    private LegalConditionTab createLegalConditionTab()
    {
        return new LegalConditionTab( "Legal Condition" )
        {
            public Project getProject()
            {
                return ProjectDetailPage.this.getProject();
            }
        };
    }

    private TaskTab createTaskTab()
    {
        return new TaskTab( "Task" )
        {
            public Project getProject()
            {
                return ProjectDetailPage.this.getProject();
            }
        };
    }

    private ProjectAssigneeTab createProjectAssigneeTab()
    {
        return new ProjectAssigneeTab( "Project Assignee" )
        {
            public Project getProject()
            {
                return ProjectDetailPage.this.getProject();
            }
        };
    }

    private PriceRateTab createPriceRateTab()
    {
        return new PriceRateTab( "Price Rate Schedule" )
        {
            public PriceRateSchedule getPriceRateSchedule()
            {
                return ProjectDetailPage.this.getProject().priceRateSchedule().get();
            }
        };
    }

    public abstract Project getProject();
}