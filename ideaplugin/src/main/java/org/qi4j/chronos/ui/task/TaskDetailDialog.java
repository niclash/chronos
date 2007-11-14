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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.qi4j.chronos.ui.common.AbstractDialog;

public class TaskDetailDialog extends AbstractDialog
{
    private JTextField titleField;
    private JTextField userField;
    private JTextField createdDateField;
    private JTextField taskStatusField;
    private JTextArea descTextArea;

    private JTable commentTable;
    private JTable workEntryTable;

    public TaskDetailDialog()
    {
        super( false );
    }

    protected void initComponents()
    {
        titleField = new JTextField();
        userField = new JTextField();
        createdDateField = new JTextField();
        taskStatusField = new JTextField();

        descTextArea = new JTextArea();
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 80dlu:grow, 3dlu, right:p, 3dlu, 80dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, p, 3dlu, p, 3dlu, 80dlu";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {

    }

    protected String getDialogTitle()
    {
        return "Task Detail";
    }
}
