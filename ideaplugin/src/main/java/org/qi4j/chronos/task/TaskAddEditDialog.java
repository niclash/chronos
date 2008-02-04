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
package org.qi4j.chronos.task;

import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JComboBox;
import org.qi4j.chronos.common.AddEditDialog;
import org.qi4j.chronos.common.text.JMaxLengthTextArea;
import org.qi4j.chronos.common.text.JMaxLengthTextField;
import org.qi4j.chronos.common.text.ReadOnlyTextField;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.TaskStatus;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.util.UiUtil;

public abstract class TaskAddEditDialog extends AddEditDialog
{
    private JMaxLengthTextField titleField;
    private ReadOnlyTextField userField;
    private ReadOnlyTextField createdDateField;

    private JComboBox taskStatusComboBox;

    private JMaxLengthTextArea descTextArea;

    public TaskAddEditDialog( Project project )
    {
        super( project );
        validate();
    }

    protected void initComponents()
    {
        titleField = new JMaxLengthTextField( Task.TITLE_LEN );
        userField = new ReadOnlyTextField( getCreatedBy().getFullname() );
        createdDateField = new ReadOnlyTextField( "--" );

        taskStatusComboBox = new JComboBox( TaskStatus.values() );

        descTextArea = new JMaxLengthTextArea( Task.DESCRIPTION_LEN );
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 80dlu:grow, 3dlu, right:p, 3dlu, 80dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, p, 3dlu, p, 3dlu, 80dlu";
    }

    public void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.addLabel( "Created by", cc.xy( 1, 1 ) );
        builder.add( userField, cc.xy( 3, 1 ) );

        builder.addLabel( "Created Date", cc.xy( 5, 1 ) );
        builder.add( createdDateField, cc.xy( 7, 1 ) );

        builder.addLabel( "Status", cc.xy( 1, 3 ) );
        builder.add( taskStatusComboBox, cc.xy( 3, 3 ) );

        builder.addLabel( "Title", cc.xy( 1, 5 ) );
        builder.add( titleField, cc.xyw( 3, 5, 5 ) );

        builder.addLabel( "Description", cc.xy( 1, 7, "right,top" ) );
        builder.add( UiUtil.createScrollPanel( descTextArea ), cc.xyw( 3, 7, 5, "fill, fill" ) );
    }

    protected void assignFieldValueToTask( TaskEntityComposite task )
    {
        task.user().set( getCreatedBy() );
        task.title().set( titleField.getText() );
        task.description().set( descTextArea.getText() );
        task.taskStatus().set( (TaskStatus) taskStatusComboBox.getSelectedItem() );
    }

    protected void assignTaskToFieldValue( TaskEntityComposite task )
    {
        titleField.setText( task.title().get() );
        descTextArea.setText( task.description().get() );
        createdDateField.setText( DateUtil.formatDateTime( task.createdDate().get() ) );
        taskStatusComboBox.setSelectedItem( task.taskStatus().get() );
    }

    public abstract User getCreatedBy();
}
