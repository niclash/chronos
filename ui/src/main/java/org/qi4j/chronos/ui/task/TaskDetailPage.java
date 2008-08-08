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
package org.qi4j.chronos.ui.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.ui.comment.CommentTab;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;
import org.qi4j.chronos.ui.workentry.WorkEntryDataProvider;
import org.qi4j.chronos.ui.workentry.WorkEntryTab;

public final class TaskDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_FEEDBACK_PANEL = "feedbackPanel";
    private static final String WICKET_ID_TASK_DETAIL_FORM = "taskDetailForm";

    private Page basePage;

    public TaskDetailPage( final Page basePage, IModel<Task> task )
    {
        this.basePage = basePage;

        add( new FeedbackPanel( WICKET_ID_FEEDBACK_PANEL ) );
        add( new TaskMasterDetailForm( WICKET_ID_TASK_DETAIL_FORM, task ) );
    }

    private class TaskMasterDetailForm extends Form
    {
        private static final long serialVersionUID = 1L;

        private static final String WICKET_ID_USER_FIELD = "userField";
        private static final String WICKET_ID_TITLE = "title";
        private static final String WICKET_ID_CREATE_DATE = "createDate";
        private static final String WICKET_ID_DESCRIPTION = "description";
        private static final String WICKET_ID_SUBMIT_BUTTON = "submitButton";
        private static final String WICKET_ID_TABBED_PANEL = "tabbedPanel";
        private static final String WICKET_ID_COMMENT = "Comment";
        private static final String WICKET_ID_WORK_ENTRIES = "WorkEntries";

        public TaskMasterDetailForm( String id, IModel<Task> task )
        {
            super( id );

            ChronosCompoundPropertyModel<Task> model = new ChronosCompoundPropertyModel<Task>( task.getObject() );

            TextField<String> userField = new TextField<String>( WICKET_ID_USER_FIELD, model.<String>bind( "user.fullName" ) );
            TextField<String> titleField = new TextField<String>( WICKET_ID_TITLE, model.<String>bind( WICKET_ID_TITLE ) );
            TextField<String> createDateField = new TextField<String>( WICKET_ID_CREATE_DATE, model.<String>bind( "createdDate" ) );
            TextArea<String> descriptionTextArea = new TextArea<String>( WICKET_ID_DESCRIPTION, model.<String>bind( WICKET_ID_DESCRIPTION ) );

            final Button submitButton = new Button( WICKET_ID_SUBMIT_BUTTON, new Model<String>( "Return" ) )
            {
                private static final long serialVersionUID = 1L;

                public void onSubmit()
                {
                    setResponsePage( basePage );
                }
            };

            List<ITab> tabs = new ArrayList<ITab>();
            tabs.add( createCommentTab() );
            tabs.add( createWorkEntryTab() );

            TabbedPanel tabbedPanel = new TabbedPanel( WICKET_ID_TABBED_PANEL, tabs );

            add( userField );
            add( titleField );
            add( createDateField );
            add( descriptionTextArea );
            add( submitButton );
            add( tabbedPanel );
        }

        private WorkEntryTab createWorkEntryTab()
        {
            ChronosDetachableModel<Task> task = new ChronosDetachableModel<Task>( getTask() );
            ChronosDetachableModel<ProjectAssignee> projectAssignee = new ChronosDetachableModel<ProjectAssignee>( getProjectAssignee() );

            return new WorkEntryTab( WICKET_ID_WORK_ENTRIES, task, new WorkEntryDataProvider( task ), projectAssignee );
        }

        private CommentTab createCommentTab()
        {
            ChronosDetachableModel<Task> task = new ChronosDetachableModel<Task>( getTask() );
            return new CommentTab( WICKET_ID_COMMENT, task );
        }
    }

    private void addingWorkEntry( WorkEntry workEntry )
    {
        Task task = getTask();

        //add workEntry to task
        task.workEntries().add( workEntry );

        //update task
        // TODO migrate
//        getServices().getTaskService().update( task );
    }

    private ProjectAssignee getProjectAssignee()
    {
        //TODO bp. This may return null if current user is Customer or ACCOUNT_ADMIN.
        AuthenticatedWebSession authenticatedSession = (AuthenticatedWebSession) getSession();
        Roles userRoles = authenticatedSession.getRoles();
        if( userRoles.contains( SystemRole.STAFF ) )
        {
/*
            TODO kamil: migrate
            Project project = getServices().getTaskService().getParent( (TaskEntityComposite) getTask() );

            Staff staff = (Staff) getChronosSession().getUser();

            ProjectAssignee projectAssignee = getServices().getProjectAssigneeService().getProjectAssignee( project, staff );

            return projectAssignee;
*/
            Staff staff = (Staff) getChronosSession().getUser();
            for( Project project : getAccount().projects() )
            {
                for( ProjectAssignee projectAssignee : project.projectAssignees() )
                {
                    if( staff.equals( projectAssignee.staff().get() ) )
                    {
                        return projectAssignee;
                    }
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    public Task getTask()
    {
        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( (Task) getDefaultModelObject() );
    }
}
