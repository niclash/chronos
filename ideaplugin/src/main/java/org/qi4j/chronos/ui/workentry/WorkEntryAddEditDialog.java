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
import java.util.Date;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.ui.common.AddEditDialog;
import org.qi4j.chronos.ui.common.JDateTime;
import org.qi4j.chronos.ui.common.ReadOnlyTextField;
import org.qi4j.chronos.ui.util.UiUtil;
import org.qi4j.chronos.util.ChronosUtil;

public abstract class WorkEntryAddEditDialog extends AddEditDialog
{
    private ReadOnlyTextField createdDateField;
    private ReadOnlyTextField userField;

    private JTextField titleField;
    private JTextArea descTextArea;

    private JDateTime startDateTime;
    private JDateTime endDatetime;

    public WorkEntryAddEditDialog()
    {

    }

    protected void initComponents()
    {
        titleField = new JTextField();
        createdDateField = new ReadOnlyTextField( "--" );

        userField = new ReadOnlyTextField( getProjectAssignee().getStaff().getFullname() );

        descTextArea = new JTextArea();
        UiUtil.createScrollPanel( descTextArea );

        startDateTime = new JDateTime();
        endDatetime = new JDateTime();

        startDateTime.setDate( getStartedDate() );
        endDatetime.setDate( ChronosUtil.getCurrentDate() );
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 80dlu:grow, 3dlu, right:p, " +
               "3dlu, 80dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu,p,3dlu, p," +
               "3dlu,p,3dlu 80dlu";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.addLabel( "User", cc.xy( 1, 1 ) );
        builder.add( userField, cc.xy( 3, 1 ) );

        builder.addLabel( "Created Date", cc.xy( 5, 1 ) );
        builder.add( createdDateField, cc.xy( 7, 1 ) );

        builder.addLabel( "Start Time", cc.xy( 1, 3 ) );
        builder.add( startDateTime, cc.xyw( 3, 3, 5 ) );

        builder.addLabel( "End time", cc.xy( 1, 5 ) );
        builder.add( endDatetime, cc.xyw( 3, 5, 5 ) );

        builder.addLabel( "Title", cc.xy( 1, 7 ) );
        builder.add( titleField, cc.xyw( 3, 7, 5 ) );

        builder.addLabel( "Description", cc.xy( 1, 9, "right,top" ) );
        builder.add( UiUtil.createScrollPanel( descTextArea ), cc.xyw( 3, 9, 5, "fill, fill" ) );
    }

    protected void assignFieldValueToWorkEntry( WorkEntryEntityComposite workEntry )
    {
        workEntry.setDescription( descTextArea.getText() );
        workEntry.setEndTime( endDatetime.getDate() );
        workEntry.setStartTime( startDateTime.getDate() );
        workEntry.setTitle( titleField.getText() );
        workEntry.setProjectAssignee( getProjectAssignee() );
    }

    protected void assignWorkEntryToFieldValue( WorkEntryEntityComposite workEntry )
    {
        descTextArea.setText( workEntry.getDescription() );
        endDatetime.setDate( workEntry.getEndTime() );
        startDateTime.setDate( workEntry.getStartTime() );
        titleField.setText( workEntry.getTitle() );
    }

    public Date getStartedDate()
    {
        //override this if startedDate should be get it from somewhere else.
        return ChronosUtil.getCurrentDate();
    }

    public abstract ProjectAssigneeEntityComposite getProjectAssignee();
}
