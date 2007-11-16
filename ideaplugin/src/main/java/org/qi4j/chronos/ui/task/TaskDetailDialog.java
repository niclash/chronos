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

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JTextArea;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.ui.comment.CommentListPanel;
import org.qi4j.chronos.ui.common.AbstractDialog;
import org.qi4j.chronos.ui.common.ChronosTabbedPanel;
import org.qi4j.chronos.ui.common.ReadOnlyTextField;
import org.qi4j.chronos.ui.util.UiUtil;
import org.qi4j.chronos.ui.workentry.WorkEntryListPanel;
import org.qi4j.chronos.util.DateUtil;

public abstract class TaskDetailDialog extends AbstractDialog
{
    private ReadOnlyTextField titleField;
    private ReadOnlyTextField userField;
    private ReadOnlyTextField createdDateField;
    private ReadOnlyTextField taskStatusField;
    private JTextArea descTextArea;

    private ChronosTabbedPanel tabbedPanel;

    private CommentListPanel commentListPanel;
    private WorkEntryListPanel workEntryListPanel;

    public TaskDetailDialog()
    {
        super( false );

        initData();
    }

    private void initData()
    {
        TaskEntityComposite task = getTask();

        titleField.setText( task.getTitle() );
        userField.setText( task.getUser().getFullname() );
        createdDateField.setText( DateUtil.formatDateTime( task.getCreatedDate() ) );
        taskStatusField.setText( task.getTaskStatus().toString() );
        descTextArea.setText( task.getDescription() );

        commentListPanel.initData( getServices().getCommentService().findAll( task ) );
        workEntryListPanel.initData( getServices().getWorkEntryService().findAll( task ) );
    }

    protected void initComponents()
    {
        titleField = new ReadOnlyTextField();
        userField = new ReadOnlyTextField();
        createdDateField = new ReadOnlyTextField();
        taskStatusField = new ReadOnlyTextField();

        descTextArea = new JTextArea();

        tabbedPanel = new ChronosTabbedPanel();

        commentListPanel = new CommentListPanel()
        {
            public void addingComment( CommentComposite comment )
            {
                TaskDetailDialog.this.addingComment( comment );
            }
        };

        workEntryListPanel = new WorkEntryListPanel();

        tabbedPanel.addTab( "Comments", commentListPanel );
        tabbedPanel.addTab( "WorkEntries", workEntryListPanel );
    }

    private void addingComment( CommentComposite comment )
    {
        //TODO bp. Duplicate code found in TaskNewCommentAction
        TaskEntityComposite task = getTask();

        //add comment to task
        task.addComment( comment );

        UiUtil.showMsgDialog( "Comment added", "New comment is added successfully." );

        //update task
        getServices().getTaskService().update( task );
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 80dlu:grow, 3dlu, right:p, 3dlu, 80dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, p, 3dlu, p, 3dlu, 80dlu, 3dlu, 150dlu";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.addLabel( "User", cc.xy( 1, 1 ) );
        builder.add( userField, cc.xy( 3, 1 ) );

        builder.addLabel( "Created Date", cc.xy( 5, 1 ) );
        builder.add( createdDateField, cc.xy( 7, 1 ) );

        builder.addLabel( "Status", cc.xy( 1, 3 ) );
        builder.add( taskStatusField, cc.xy( 3, 3 ) );

        builder.addLabel( "Title", cc.xy( 1, 5 ) );
        builder.add( titleField, cc.xyw( 3, 5, 5 ) );

        builder.addLabel( "Description", cc.xy( 1, 7, "right,top" ) );
        builder.add( UiUtil.createScrollPanel( descTextArea ), cc.xyw( 3, 7, 5, "fill, fill" ) );

        builder.add( tabbedPanel, cc.xyw( 1, 9, 7, "fill,fill" ) );
    }

    protected String getDialogTitle()
    {
        return "Task Detail";
    }

    public abstract TaskEntityComposite getTask();
}
