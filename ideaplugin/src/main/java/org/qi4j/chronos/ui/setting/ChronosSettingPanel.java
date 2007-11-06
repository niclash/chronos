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

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.qi4j.chronos.config.ChronosConfig;
import org.qi4j.chronos.ui.common.AbstractPanel;

public class ChronosSettingPanel extends AbstractPanel
{
    private JTextField loginIdField;
    private JPasswordField passwordField;

    private JComboBox accountComboBox;
    private JComboBox projectComboBox;

    private JCheckBox isPromptLoginDialogCheckBox;

    private JButton qi4jSettingButton;
    private JButton loginButton;
    private JButton logoutButton;

    private String qi4jSessionIp;
    private int qi4jSessionPort;

    public ChronosSettingPanel()
    {
        renderComponent();
    }

    protected void initComponents()
    {
        loginIdField = new JTextField();
        passwordField = new JPasswordField();

        accountComboBox = new JComboBox( getAvailableAccount() );
        projectComboBox = new JComboBox( getAssignedProjects() );

        qi4jSettingButton = new JButton( "Qi4j Session Setting" );

        isPromptLoginDialogCheckBox = new JCheckBox( "Prompt up login dialog on startup." );

        loginButton = new JButton( "Login" );
        logoutButton = new JButton( "Logout" );

        initActions();
    }

    private String[] getAvailableAccount()
    {
        //TODO bp. fixme
        return new String[]{ "Jayway Malaysia", "Microsoft", "Google" };
    }

    private String[] getAssignedProjects()
    {
        //TODO bp. fixme
        return new String[]{ "Chronos", "Qi4j", "Pax Runner" };
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 120dlu, 1dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, p,3dlu,p," +
               "3dlu,p, 3dlu, p,3dlu," +
               "p,3dlu,p, 3dlu, p";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( qi4jSettingButton, cc.xyw( 1, 1, 3 ) );

        builder.addSeparator( "Login Setting", cc.xyw( 1, 3, 4 ) );

        builder.add( new JLabel( "Account" ), cc.xy( 1, 5 ) );
        builder.add( accountComboBox, cc.xy( 3, 5 ) );

        builder.add( new JLabel( "Login Id" ), cc.xy( 1, 7 ) );
        builder.add( loginIdField, cc.xy( 3, 7 ) );

        builder.add( new JLabel( "Password" ), cc.xy( 1, 9 ) );
        builder.add( passwordField, cc.xy( 3, 9 ) );

        builder.add( isPromptLoginDialogCheckBox, cc.xyw( 3, 11, 2, "left, center" ) );

        builder.addSeparator( "Project Setting", cc.xyw( 1, 13, 4 ) );

        builder.add( new JLabel( "Project" ), cc.xy( 1, 15 ) );
        builder.add( projectComboBox, cc.xy( 3, 15 ) );
    }

    private void initActions()
    {
        qi4jSettingButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                handleQi4jSettingClicked();
            }
        } );

        loginButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                handleLoginClicked();
            }
        } );

        logoutButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                handleLogoutClicked();
            }
        } );
    }

    private void handleLogoutClicked()
    {
        //TODO bp. fixme
    }

    private void handleLoginClicked()
    {
        //TODO bp. fixme
    }

    private void handleQi4jSettingClicked()
    {
        Qi4jSessionSettingDialog dialog = new Qi4jSessionSettingDialog();

        dialog.setQi4jSessionIp( qi4jSessionIp );
        dialog.setQi4jSessionPort( qi4jSessionPort );

        dialog.show();

        qi4jSessionIp = dialog.getQi4jSessionIp();
        qi4jSessionPort = dialog.getQi4jSessionPort();
    }

    public void readChronosConfig( ChronosConfig chronosConfig )
    {
        qi4jSessionIp = chronosConfig.getQi4jSessionIp();
        qi4jSessionPort = chronosConfig.getQi4jSessionPort();

        loginIdField.setText( chronosConfig.getLoginId() );
        isPromptLoginDialogCheckBox.setEnabled( chronosConfig.isPromptUpLoginOnStartup() );
    }

    public void updateChronosConfig( ChronosConfig chronosConfig )
    {

    }
}
