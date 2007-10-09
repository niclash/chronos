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
package org.qi4j.chronos.ui.taskassignee;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.workentry.WorkEntryTab;

public abstract class TaskAssigneeDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public TaskAssigneeDetailPage( Page returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new TaskAssigneeDetailForm( "taskAssigneeDetailForm" ) );
    }

    private class TaskAssigneeDetailForm extends Form
    {
        private Button submitButton;
        private SimpleTextField firstNameField;
        private SimpleTextField lastNameField;
        private TabbedPanel tabbedPanel;

        public TaskAssigneeDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            TaskAssigneeEntityComposite taskAssignee = getTaskAssignee();

            firstNameField = new SimpleTextField( "firstNameField", taskAssignee.getProjectAssignee().getStaff().getFirstName() );
            lastNameField = new SimpleTextField( "lastNameField", taskAssignee.getProjectAssignee().getStaff().getLastName() );

            List<AbstractTab> tabs = new ArrayList<AbstractTab>();

            tabs.add( createWorkEntryTab() );

            tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            add( firstNameField );
            add( lastNameField );
            add( tabbedPanel );
            add( submitButton );
        }
    }

    private WorkEntryTab createWorkEntryTab()
    {
        return new WorkEntryTab( "Work Entry" )
        {
            public TaskAssigneeEntityComposite getTaskAssignee()
            {
                return TaskAssigneeDetailPage.this.getTaskAssignee();
            }

            public List<WorkEntryEntityComposite> dataList( int first, int count )
            {
                return getServices().getWorkEntryService().findAll( getTaskAssignee(), new FindFilter( first, count ) );
            }

            public int getSize()
            {
                return getServices().getWorkEntryService().countAll( getTaskAssignee() );
            }
        };
    }

    public abstract TaskAssigneeEntityComposite getTaskAssignee();
}
