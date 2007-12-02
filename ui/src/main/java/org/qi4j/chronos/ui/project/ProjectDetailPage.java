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
import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.contactperson.ContactPersonTable;
import org.qi4j.chronos.ui.legalcondition.LegalConditionTab;
import org.qi4j.chronos.ui.pricerate.PriceRateTab;
import org.qi4j.chronos.ui.projectassignee.ProjectAssigneeTab;
import org.qi4j.chronos.ui.task.TaskTab;
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

            projectNameField = new SimpleTextField( "projectNameField", project.getName() );
            formalReferenceField = new SimpleTextField( "formalReferenceField", project.getReference() );
            projectStatusField = new SimpleTextField( "projectStatusField", project.getProjectStatus().toString() );

            projectOwnerField = new SimpleTextField( "customerField", project.getCustomer().getName() );

            String primaryContact = project.getPrimaryContactPerson().getFullname();
            primaryContactField = new SimpleTextField( "primaryContactField", primaryContact );

            estimateStartTimeField = new SimpleTextField( "estimateStartTimeField", DateUtil.formatDate( project.getEstimateTime().getStartTime() ) );
            estimateEndTimeField = new SimpleTextField( "estimateEndTimeField", DateUtil.formatDate( project.getEstimateTime().getEndTime() ) );

            String actualStartTime = project.getActualTime().getStartTime() == null ? "" : DateUtil.formatDate( project.getActualTime().getStartTime() );
            String actualEndTime = project.getActualTime().getEndTime() == null ? "" : DateUtil.formatDate( project.getActualTime().getEndTime() );

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

                public CustomerEntityComposite getCustomer()
                {
                    return getProject().getCustomer();
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

            if( project.getProjectStatus() != ProjectStatus.CLOSED )
            {
                actualTimeContainer.setVisible( false );
            }
        }
    }

    private WorkEntryTab createWorkEntryTab()
    {
        return new WorkEntryTab( "WorkEntries" )
        {
            public void addingWorkEntry( WorkEntryEntityComposite workEntry )
            {
                ProjectDetailPage.this.addingWorkEntry( workEntry );
            }

            public ProjectAssigneeEntityComposite getProjectAssignee()
            {
                return ProjectDetailPage.this.getProjectAssignee();
            }

            public HasWorkEntries getHasWorkEntries()
            {
                return getProject();
            }
        };
    }

    private void addingWorkEntry( WorkEntryEntityComposite workEntry )
    {
        ProjectEntityComposite project = getProject();

        //add workEntry to project
        project.addWorkEntry( workEntry );

        //update project
        getServices().getProjectService().update( project );
    }

    private ProjectAssigneeEntityComposite getProjectAssignee()
    {
        //TODO bp. This may return null if current user is Customer or ACCOUNT_ADMIN.
        //TODO got better way to handle this?
        if( getChronosSession().isStaff() )
        {
            StaffEntityComposite staff = (StaffEntityComposite) getChronosSession().getUser();

            ProjectAssigneeEntityComposite projectAssignee = getServices().getProjectAssigneeService().getProjectAssignee( getProject(), staff );

            return projectAssignee;
        }

        return null;
    }

    private LegalConditionTab createLegalConditionTab()
    {
        return new LegalConditionTab( "Legal Condition" )
        {
            public ProjectEntityComposite getProject()
            {
                return ProjectDetailPage.this.getProject();
            }
        };
    }

    private TaskTab createTaskTab()
    {
        return new TaskTab( "Task" )
        {
            public ProjectEntityComposite getProject()
            {
                return ProjectDetailPage.this.getProject();
            }
        };
    }

    private ProjectAssigneeTab createProjectAssigneeTab()
    {
        return new ProjectAssigneeTab( "Project Assignee" )
        {
            public ProjectEntityComposite getProject()
            {
                return ProjectDetailPage.this.getProject();
            }
        };
    }

    private PriceRateTab createPriceRateTab()
    {
        return new PriceRateTab( "Price Rate Schedule" )
        {
            public PriceRateScheduleComposite getPriceRateSchedule()
            {
                return ProjectDetailPage.this.getProject().getPriceRateSchedule();
            }
        };
    }

    public abstract ProjectEntityComposite getProject();
}