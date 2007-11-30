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

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import org.jdom.Element;
import org.jetbrains.annotations.Nls;
import org.qi4j.chronos.common.AbstractPanel;
import org.qi4j.chronos.setting.ChronosServerSettingDialog;

public class ChronosProjectComponent implements ProjectComponent, Configurable, JDOMExternalizable
{
    //TODO fix icon
    private static final Icon ICON = IconLoader.getIcon( "/org/qi4j/chronos/ui/setting/icon.png" );

    public final static String DISPLAY_NAME = "Chronos Setting";
    public final static String COMPONENT_NAME = "ChronosSetting";

    private ChronosConfig chronosConfig;

    private ChronosApp chronosApp;

    public ChronosProjectComponent()
    {
        chronosConfig = new ChronosConfig();
    }

    public static ChronosProjectComponent getInstance( Project project )
    {
        return project.getComponent( ChronosProjectComponent.class );
    }

    public static ChronosProjectComponent getInstance( DataContext dataContext )
    {
        Project project = (Project) dataContext.getData( DataConstants.PROJECT );

        return getInstance( project );
    }

    public static ChronosProjectComponent getInstance()
    {
        return getInstance( DataManager.getInstance().getDataContext() );
    }

    public ChronosApp getChronosApp()
    {
        return getChronosApp();
    }

    public void projectOpened()
    {
        chronosApp = new ChronosApp( chronosConfig );

        chronosApp.start();
    }

    public void projectClosed()
    {
        chronosApp.stop();
    }

    public String getComponentName()
    {
        return COMPONENT_NAME;
    }


    public void initComponent()
    {
        //nothing
    }

    public void disposeComponent()
    {
        //nothing
    }

    @Nls public String getDisplayName()
    {
        return DISPLAY_NAME;
    }

    public Icon getIcon()
    {
        return ICON;
    }

    public static ImageIcon getIcon( String packageName, Class clazz ) throws IOException
    {
        return new ImageIcon( ImageIO.read( clazz.getClassLoader().getResourceAsStream( packageName ) ) );
    }

    public String getHelpTopic()
    {
        return null;
    }

    public JComponent createComponent()
    {
        return new ChronosProjectPanel();
    }

    public boolean isModified()
    {
        return false;
    }

    public void apply() throws ConfigurationException
    {
        //nothing
    }

    public void reset()
    {
        //nothing
    }

    public void disposeUIResources()
    {
        //nothing
    }

    private class ChronosProjectPanel extends AbstractPanel
    {
        private JButton chronosServerSettingButton;
        private JButton userLoginSettingButton;

        public ChronosProjectPanel()
        {
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
            ChronosServerSettingDialog dialog = new ChronosServerSettingDialog();

            dialog.show();

            if( dialog.isOK() )
            {
                //TODO bp. 
            }
        }

        private void handleUserLoginSettingClicked()
        {
            //TODO bp.
        }
    }

    public void readExternal( Element element ) throws InvalidDataException
    {
        chronosConfig.readExternal( element );
    }

    public void writeExternal( Element element ) throws WriteExternalException
    {
        chronosConfig.writeExternal( element );
    }
}
