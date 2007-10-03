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
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatus;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.BorderPanel;
import org.qi4j.chronos.ui.common.BorderPanelWrapper;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.common.tab.BaseTab;
import org.qi4j.chronos.ui.contactperson.ContactPersonTable;
import org.qi4j.chronos.ui.pricerate.PriceRateTab;
import org.qi4j.chronos.ui.projectassignee.ProjectAssigneeTab;
import org.qi4j.chronos.ui.workentry.WorkEntryTab2;
import org.qi4j.chronos.util.DateUtil;

public abstract class ProjectDetailPage extends LeftMenuNavPage
{
    private BasePage returnPage;

    public ProjectDetailPage( BasePage returnPage )
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

            String primaryContact = project.getPrimaryContactPerson().getFirstName() + project.getPrimaryContactPerson().getLastName();
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

            tabs.add( new ContactPersonTab() );
            tabs.add( new PriceRateTab( "Price Rate Schedule" )
            {
                public PriceRateScheduleComposite getPriceRateSchedule()
                {
                    return ProjectDetailPage.this.getProject().getPriceRateSchedule();
                }
            } );

            tabs.add( new ProjectAssigneeTab( "Project Assignee" )
            {
                public ProjectEntityComposite getProject()
                {
                    return ProjectDetailPage.this.getProject();
                }
            } );

            tabs.add( new WorkEntryTab2( "Work Entry" )
            {
                public List<WorkEntryEntityComposite> dataList( int first, int count )
                {
                    return getWorkEntryService().findAll( getProject(), new FindFilter( first, count ) );
                }

                public int getSize()
                {
                    return getWorkEntryService().countAll( getProject() );
                }
            } );


            tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

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

            if( project.getProjectStatus() != ProjectStatus.CLOSED )
            {
                actualTimeContainer.setVisible( false );
            }
        }
    }

    private class ContactPersonTab extends BaseTab
    {
        public ContactPersonTab()
        {
            super( "Contact Person" );
        }

        public BorderPanel getBorderPanel( String panelId )
        {
            BorderPanelWrapper borderPanelWrapper = new BorderPanelWrapper( panelId )
            {
                public Panel getWrappedPanel( String panelId )
                {
                    ContactPersonTable table = new ContactPersonTable( panelId )
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

                    table.setItemPerPage( 1000 );

                    table.setNavigatorVisible( false );
                    table.setNoActionBar( true );

                    return table;
                }
            };

            return borderPanelWrapper;
        }
    }

    private WorkEntryService getWorkEntryService()
    {
        return ChronosWebApp.getServices().getWorkEntryService();
    }

    public abstract ProjectEntityComposite getProject();
}