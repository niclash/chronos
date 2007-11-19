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
package org.qi4j.chronos.ui.workentry;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.ui.comment.CommentAddDialog;
import org.qi4j.chronos.ui.comment.CommentListPanel;
import org.qi4j.chronos.ui.common.AbstractDialog;
import org.qi4j.chronos.ui.common.ChronosTabbedPanel;
import org.qi4j.chronos.ui.common.ReadOnlyTextArea;
import org.qi4j.chronos.ui.common.ReadOnlyTextField;
import org.qi4j.chronos.ui.util.UiUtil;
import org.qi4j.chronos.util.DateUtil;

public abstract class WorkEntryDetailDialog extends AbstractDialog
{
    private ReadOnlyTextField createdDateField;
    private ReadOnlyTextField userField;

    private ReadOnlyTextField titleField;
    private ReadOnlyTextArea descTextArea;

    private ReadOnlyTextField startDateTime;
    private ReadOnlyTextField endDatetime;

    private ReadOnlyTextField durationField;

    private ChronosTabbedPanel tabbedPanel;
    private CommentListPanel commentListPanel;

    public WorkEntryDetailDialog()
    {
        super( true );

        initData();
    }

    private void initData()
    {
        WorkEntryEntityComposite workEntry = getWorkEntry();

        createdDateField.setText( DateUtil.formatDateTime( workEntry.getCreatedDate() ) );
        userField.setText( workEntry.getProjectAssignee().getStaff().getFullname() );
        titleField.setText( workEntry.getTitle() );
        descTextArea.setText( workEntry.getDescription() );
        startDateTime.setText( DateUtil.formatDateTime( workEntry.getStartTime() ) );
        endDatetime.setText( DateUtil.formatDateTime( workEntry.getEndTime() ) );
        durationField.setText( DateUtil.getTimeDifferent( workEntry.getStartTime(), workEntry.getEndTime() ) );

        commentListPanel.initData( getServices().getCommentService().findAll( workEntry ) );
    }

    protected void initComponents()
    {
        createdDateField = new ReadOnlyTextField();
        userField = new ReadOnlyTextField();
        titleField = new ReadOnlyTextField();

        descTextArea = new ReadOnlyTextArea();

        startDateTime = new ReadOnlyTextField();
        endDatetime = new ReadOnlyTextField();

        durationField = new ReadOnlyTextField();

        tabbedPanel = new ChronosTabbedPanel();

        commentListPanel = new CommentListPanel()
        {
            public CommentAddDialog newCommentAddDialog()
            {
                return new WorkEntryCommentAddDialog()
                {
                    public WorkEntryEntityComposite getWorkEntry()
                    {
                        return WorkEntryDetailDialog.this.getWorkEntry();
                    }
                };
            }
        };

        tabbedPanel.addTab( "Comments", commentListPanel );
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 80dlu:grow, 3dlu, right:p, " +
               "3dlu, 70dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu,p,3dlu, p," +
               "3dlu,p,3dlu 60dlu, 3dlu," +
               "120dlu";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.addLabel( "Added By", cc.xy( 1, 1 ) );
        builder.add( userField, cc.xy( 3, 1 ) );

        builder.addLabel( "Created Date", cc.xy( 5, 1 ) );
        builder.add( createdDateField, cc.xy( 7, 1 ) );

        builder.addLabel( "Start Time", cc.xy( 1, 3 ) );
        builder.add( startDateTime, cc.xy( 3, 3 ) );

        builder.addLabel( "End time", cc.xy( 5, 3 ) );
        builder.add( endDatetime, cc.xy( 7, 3 ) );

        builder.addLabel( "Duration", cc.xy( 1, 5 ) );
        builder.add( durationField, cc.xy( 3, 5 ) );

        builder.addLabel( "Title", cc.xy( 1, 7 ) );
        builder.add( titleField, cc.xyw( 3, 7, 5 ) );

        builder.addLabel( "Description", cc.xy( 1, 9, "right,top" ) );
        builder.add( UiUtil.createScrollPanel( descTextArea ), cc.xyw( 3, 9, 5, "fill, fill" ) );

        builder.add( tabbedPanel, cc.xyw( 1, 11, 7, "fill,fill" ) );
    }

    protected String getDialogTitle()
    {
        return "WorkEntry Detail";
    }

    public abstract WorkEntryEntityComposite getWorkEntry();

}
