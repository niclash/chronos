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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.ui.common.AddEditDialog;

public abstract class TaskAddEditDialog extends AddEditDialog
{
    private JTextField titleField;
    private JTextField userField;
    private JTextField createdDateField;

    private JTextArea descTextArea;
    private JScrollPane descScrollPanel;

    public TaskAddEditDialog( )
    {

    }

    protected void initComponents()
    {
        titleField = new JTextField();
        userField = new JTextField();
        createdDateField = new JTextField( "--" );

        createdDateField.setEditable( false );
        userField.setEditable( false );

        descTextArea = new JTextArea();

        descScrollPanel = new JScrollPane( descTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 80dlu:grow, 3dlu, right:p, 3dlu, 80dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, p, 3dlu, 80dlu";
    }

    public void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( new JLabel( "User" ), cc.xy( 1, 1 ) );
        builder.add( userField, cc.xy( 3, 1 ) );

        builder.add( new JLabel( "Created Date" ), cc.xy( 5, 1 ) );
        builder.add( createdDateField, cc.xy( 7, 1 ) );

        builder.add( new JLabel( "Title" ), cc.xy( 1, 3 ) );
        builder.add( titleField, cc.xyw( 3, 3, 5 ) );

        builder.add( new JLabel( "Description" ), cc.xy( 1, 5, "right,top" ) );
        builder.add( descScrollPanel, cc.xyw( 3, 5, 5, "fill, fill" ) );
    }

    protected void assignFieldValueToTask( TaskEntityComposite task )
    {
        task.setTitle( titleField.getText() );
        task.setDescription( descTextArea.getText() );
    }

    protected void assignTaskToFieldValue( TaskEntityComposite task )
    {
        titleField.setText( task.getTitle() );
        descTextArea.setText( task.getDescription() );
    }

    public final void handleOkClicked()
    {
        //TODO bp. this is weak, use careListener to enable/disable okButton
        //and diasplay a warning msg.
        boolean isRejected = false;

        if( titleField.getText() == null || titleField.getText().length() == 0 )
        {
            isRejected = true;
        }

        if( descTextArea.getText() == null || descTextArea.getText().length() == 0 )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public abstract void onSubmitting();
}
