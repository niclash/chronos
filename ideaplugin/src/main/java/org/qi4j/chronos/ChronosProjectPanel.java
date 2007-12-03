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
package org.qi4j.chronos;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import org.qi4j.chronos.common.AbstractPanel;

public class ChronosProjectPanel
    extends AbstractPanel
{
    private JButton chronosServerSettingButton;
    private JButton userLoginSettingButton;

    private ChronosApp chronosApp;

    public ChronosProjectPanel( ChronosApp chronosApp )
    {
        this.chronosApp = chronosApp;
        init();
    }

    protected String getLayoutColSpec()
    {
        return "100dlu,1dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, p";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.setDefaultDialogBorder();

        builder.add( chronosServerSettingButton, cc.xy( 1, 1 ) );
        builder.add( userLoginSettingButton, cc.xy( 1, 3 ) );
    }

    protected void initComponents()
    {
        chronosServerSettingButton = new JButton( "Chronos Server Setting" );
        userLoginSettingButton = new JButton( "User Login Setting" );

        addListeners();
    }

    private void addListeners()
    {
        chronosServerSettingButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                handleChronosServerSettingClicked();
            }
        } );

        userLoginSettingButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                handleUserLoginSettingClicked();
            }
        } );
    }

    private void handleChronosServerSettingClicked()
    {
        boolean isOk = chronosApp.showChronosServerSettingDialog();

        if( isOk )
        {
            chronosApp.resetOrConnectToChronosServer();
        }
    }

    private void handleUserLoginSettingClicked()
    {
        boolean isOk = chronosApp.showUserPassSettingDialog();

        if( isOk )
        {
            chronosApp.resetOrConnectToChronosServer();
        }
    }
}
