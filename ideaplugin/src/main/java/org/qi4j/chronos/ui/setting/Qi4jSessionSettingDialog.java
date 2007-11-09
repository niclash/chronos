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
package org.qi4j.chronos.ui.setting;

import com.intellij.openapi.ui.DialogWrapper;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.qi4j.chronos.ui.common.text.JNonFloatingPointTextField;

public class Qi4jSessionSettingDialog extends DialogWrapper
{
    private JPanel panel;

    private JTextField ipField;
    private JNonFloatingPointTextField portField;

    public Qi4jSessionSettingDialog()
    {
        super( false );

        setTitle( "Qi4j Session Setting" );
        setSize( 300, 300 );
        setModal( true );

        //MUST call this.
        init();
    }

    protected JComponent createCenterPanel()
    {
        if( panel == null )
        {
            initComponents();
        }

        return panel;
    }

    private void initComponents()
    {
        panel = new JPanel();
        ipField = new JTextField();
        portField = new JNonFloatingPointTextField( false );

        initLayout( panel );
    }

    private void initLayout( JPanel panel )
    {
        FormLayout layout = new FormLayout( "right:p, 3dlu, 120dlu, 1dlu:grow",
                                            "p, 3dlu, p,3dlu,p" );

        PanelBuilder builder = new PanelBuilder( layout, panel );
        builder.setDefaultDialogBorder();
        CellConstraints cc = new CellConstraints();

        builder.add( new JLabel( "IP Address" ), cc.xy( 1, 1 ) );
        builder.add( ipField, cc.xy( 3, 1 ) );

        builder.add( new JLabel( "Port No." ), cc.xy( 1, 3 ) );
        builder.add( portField, cc.xy( 3, 3 ) );

        builder.addSeparator( "", cc.xyw( 1, 5, 4 ) );
    }

    public void setQi4jSessionIp( String ip )
    {
        ipField.setText( ip );
    }

    public void setQi4jSessionPort( int port )
    {
        portField.setText( String.valueOf( port ) );
    }

    public int getQi4jSessionPort()
    {
        //TODO bp. validation
        return Integer.valueOf( portField.getText() );
    }

    public String getQi4jSessionIp()
    {
        return ipField.getText();
    }
}
