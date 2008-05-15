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
import java.util.Date;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.ui.comment.CommentTab;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.workentry.WorkEntryTab;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.Identity;

public class TaskDetailPage extends LeftMenuNavPage
{
    private final Page basePage;

    public TaskDetailPage( @Uses Page basePage, @Uses final String taskId )
    {
        this.basePage = basePage;

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    protected Object load()
                    {
                        return getUnitOfWork().find( taskId, TaskEntityComposite.class );
                    }
                }
            )
        );
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new TaskMasterDetailForm( "taskDetailForm", getModel() ) );
    }

    private class TaskMasterDetailForm extends Form
    {
        public TaskMasterDetailForm( String id, IModel iModel )
        {
            super( id );

            initComponents( iModel );
        }

        private void initComponents( IModel iModel )
        {
            final Task task = (Task) iModel.getObject();
            final String fullName = task.user().get().fullName().get();
            final Date createdDate = task.createdDate().get();
            final TextField userField = new SimpleTextField( "userField", fullName ); // workaround
            final TextField titleField = new TextField( "titleField", new CustomCompositeModel( iModel, "title" ) );
            final TextField createDateField =
                new SimpleTextField( "createDateField", DateUtil.formatDateTime( createdDate ) ); // workaround
            final TextArea descriptionTextArea =
                new TextArea( "descriptionTextArea", new CustomCompositeModel( iModel, "description" ) );

            final Button submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( basePage );
                }
            };

            List<AbstractTab> tabs = new ArrayList<AbstractTab>();
            tabs.add( createCommentTab() );
            tabs.add( createWorkEntryTab() );

            final TabbedPanel tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            add( userField );
            add( titleField );
            add( createDateField );
            add( descriptionTextArea );
            add( submitButton );
            add( tabbedPanel );
        }

        private WorkEntryTab createWorkEntryTab()
        {
            return new WorkEntryTab( "WorkEntries" )
            {
                public void addingWorkEntry( WorkEntry workEntry )
                {
                    TaskDetailPage.this.addingWorkEntry( workEntry );
                }

                public List<String> dataList( int first, int count )
                {
                    List<String> workEntryIdList = new ArrayList<String>();
                    for( WorkEntry workEntry : TaskDetailPage.this.getTask().workEntries() )
                    {
                        workEntryIdList.add( ( (Identity) workEntry ).identity().get() );
                    }
                    return workEntryIdList.subList( first, first + count );
                }

                public ProjectAssignee getProjectAssignee()
                {
                    return TaskDetailPage.this.getProjectAssignee();
                }

                public HasWorkEntries getHasWorkEntries()
                {
                    return TaskDetailPage.this.getTask();
                }
            };
        }

        private CommentTab createCommentTab()
        {
            return new CommentTab( "Comment" )
            {
                public List<String> dataList( int first, int count )
                {
                    List<String> commentIdList = new ArrayList<String>();
                    for( Comment comment : TaskDetailPage.this.getTask().comments() )
                    {
                        commentIdList.add( ( (Identity) comment ).identity().get() );
                    }
                    return commentIdList.subList( first, first + count );
                }

                public HasComments getHasComments()
                {
                    return TaskDetailPage.this.getTask();
                }
            };
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
        //TODO got better way to handle this?
        if( getChronosSession().isStaff() )
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
        return getUnitOfWork().dereference( (Task) getModelObject() );
    }
}
