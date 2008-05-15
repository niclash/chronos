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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.contactperson.ContactPersonTable;
import org.qi4j.chronos.ui.legalcondition.LegalConditionTab;
import org.qi4j.chronos.ui.pricerate.PriceRateTab;
import org.qi4j.chronos.ui.projectassignee.ProjectAssigneeTab;
import org.qi4j.chronos.ui.task.TaskTab;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.workentry.WorkEntryTab;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.entity.Identity;

public class ProjectDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public ProjectDetailPage( Page returnPage, final String projectId )
    {
        this.returnPage = returnPage;

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    protected Object load()
                    {
                        return getUnitOfWork().find( projectId, ProjectEntityComposite.class );
                    }
                }
            )
        );
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ProjectDetailForm( "projectDetailForm", getModel() ) );
    }

    private class ProjectDetailForm extends Form
    {
        public ProjectDetailForm( String id, IModel iModel )
        {
            super( id );

            initComponents( iModel );
        }

        private void initComponents( IModel iModel )
        {
            final Project project = (Project) iModel.getObject();
            final SimpleTextField projectNameField =
                new SimpleTextField( "projectNameField", project.name().get() );
            final SimpleTextField formalReferenceField =
                new SimpleTextField( "formalReferenceField", project.reference().get() );
            final ProjectStatusEnum projectStatus = project.projectStatus().get();
            final SimpleTextField projectStatusField =
                new SimpleTextField( "projectStatusField", projectStatus.toString() );

            final Customer projectCustomer = project.customer().get();
            final SimpleTextField projectOwnerField =
                new SimpleTextField( "customerField", projectCustomer.name().get() );

            final ContactPerson primaryContact = project.primaryContactPerson().get();
            final SimpleTextField primaryContactField =
                new SimpleTextField( "primaryContactField", primaryContact.fullName().get() );

            final TimeRange projectEstimateTime = project.estimateTime().get();
            final SimpleTextField estimateStartTimeField =
                new SimpleTextField( "estimateStartTimeField",
                                     DateUtil.formatDate( projectEstimateTime.startTime().get() ) );
            final SimpleTextField estimateEndTimeField =
                new SimpleTextField( "estimateEndTimeField",
                                     DateUtil.formatDate( projectEstimateTime.endTime().get() ) );

            final TimeRange projectActualTime = project.actualTime().get();
            String actualStartTime = null;
            String actualEndTime = null;
            if( null != projectActualTime )
            {
                if( null != projectActualTime.startTime().get() )
                {
                    actualStartTime = DateUtil.formatDate( projectActualTime.startTime().get() );
                }

                if( null != projectActualTime.endTime().get() )
                {
                    actualEndTime =  DateUtil.formatDate( projectActualTime.endTime().get() );
                }
            }
            final SimpleTextField actualStartTimeField =
                new SimpleTextField( "actualStartTimeField", actualStartTime );
            final SimpleTextField actualEndTimeField =
                new SimpleTextField( "actualEndTimeField", actualEndTime );

            final WebMarkupContainer actualTimeContainer = new WebMarkupContainer( "actualTimeContainer" );
            actualTimeContainer.add( actualStartTimeField );
            actualTimeContainer.add( actualEndTimeField );

            final Button submitButton =
                new Button( "submitButton", new Model( "Return" ) )
                {
                    public void onSubmit()
                    {
                        setResponsePage( returnPage );
                    }
                };

            final List<AbstractTab> tabs = new ArrayList<AbstractTab>();

//            tabs.add( createContactPersonTab() );
            tabs.add( createPriceRateTab() );
            tabs.add( createProjectAssigneeTab() );
            tabs.add( createTaskTab() );
            tabs.add( createWorkEntryTab() );
            tabs.add( createLegalConditionTab() );

            final TabbedPanel tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );
            final ContactPersonTable contactPersonTable = new ContactPersonTable( "contactPersonTable" )
            {
                public HasContactPersons getHasContactPersons()
                {
                    return ProjectDetailPage.this.getProject();
                }

                public Customer getCustomer()
                {
                    return ProjectDetailPage.this.getProject().customer().get();
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
            public List<String> dataList( int first, int count )
            {
                List<String> workEntryIdList = new ArrayList<String>();
                for( WorkEntry workEntry : ProjectDetailPage.this.getProject().workEntries() )
                {
                    workEntryIdList.add( ( (Identity) workEntry ).identity().get() );
                }
                return workEntryIdList.subList( first, first + count );
            }

            public ProjectAssignee getProjectAssignee()
            {
                return ProjectDetailPage.this.getProjectAssignee();
            }

            public HasWorkEntries getHasWorkEntries()
            {
                return ProjectDetailPage.this.getProject();
            }
        };
    }

    private ProjectAssignee getProjectAssignee()
    {
        //TODO bp. This may return null if current user is Customer or ACCOUNT_ADMIN.
        //TODO got better way to handle this?
        if( getChronosSession().isStaff() )
        {
/*
            TODO kamil: migrate
            Staff staff = (Staff) getChronosSession().getUser();

            ProjectAssignee projectAssignee = getServices().getProjectAssigneeService().getProjectAssignee( getProject(), staff );

            return projectAssignee;
*/
            Staff staff = (Staff) getChronosSession().getUser();
            for( ProjectAssignee projectAssignee : getProject().projectAssignees() )
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

    public Project getProject()
    {
        return getUnitOfWork().dereference( (Project) getModelObject() );
    }
}
