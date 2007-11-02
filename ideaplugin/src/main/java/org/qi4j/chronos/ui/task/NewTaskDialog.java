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

import com.intellij.openapi.ui.DialogWrapper;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jetbrains.annotations.Nullable;

public class NewTaskDialog extends DialogWrapper
{
    private JPanel panel;

    private JTextField titleField;
    private JTextArea descTextArea;

    private JScrollPane descScrollPanel;

    public NewTaskDialog()
    {
        super( false );

        setOKButtonText( "Add" );
        setTitle( "New Task" );

        setSize( 400, 300 );
        setModal( true );

        init();
    }

    @Nullable
    protected JComponent createCenterPanel()
    {
        if( panel == null )
        {
            initComponent();
        }

        return panel;
    }

    private void initComponent()
    {
        panel = new JPanel();

        titleField = new JTextField();
        descTextArea = new JTextArea();

        descScrollPanel = new JScrollPane( descTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        initLayout();
    }

    private void initLayout()
    {
        String cols = "right:p, 3dlu, 200dlu:grow";

        String rows = "p, 3dlu, 100dlu";

        FormLayout layout = new FormLayout( cols, rows );

        PanelBuilder builder = new PanelBuilder( layout, panel );
        builder.setDefaultDialogBorder();
        CellConstraints cc = new CellConstraints();

        builder.add( new JLabel( "Title" ), cc.xy( 1, 1 ) );
        builder.add( titleField, cc.xy( 3, 1 ) );

        builder.add( new JLabel( "Description" ), cc.xy( 1, 3, "right,top" ) );
        builder.add( descScrollPanel, cc.xy( 3, 3, "fill, fill" ) );
    }
}
