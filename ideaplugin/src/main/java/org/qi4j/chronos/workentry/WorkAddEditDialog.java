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
package org.qi4j.chronos.workentry;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.qi4j.chronos.ui.common.AddEditDialog;

public abstract class WorkAddEditDialog extends AddEditDialog
{
    private JTextField titleField;
    private JTextField createdDateField;
    private JTextField userField;

    private JTextArea descTextArea;

    //TODO DateChooser ??

    public void handleOkClicked()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void initComponents()
    {
        //To change body of implemented methods use File | Settings | File Templates.    
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 80dlu:grow, 3dlu, right:p, 3dlu, 80dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, 80dlu";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( new JLabel( "User" ), cc.xy( 1, 1 ) );
        builder.add( userField, cc.xy( 3, 1 ) );

        builder.add( new JLabel( "Created Date" ), cc.xy( 5, 1 ) );
        builder.add( createdDateField, cc.xy( 7, 1 ) );
    }
}
