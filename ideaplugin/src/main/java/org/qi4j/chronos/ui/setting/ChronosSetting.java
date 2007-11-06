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

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import org.jdom.Element;
import org.jetbrains.annotations.Nls;
import org.qi4j.chronos.config.ChronosConfig;

public class ChronosSetting implements ProjectComponent, Configurable, JDOMExternalizable
{
    public final static String DISPLAY_NAME = "Chronos Setting";
    public final static String COMPONENT_NAME = "ChronosSetting";

    private static final Logger LOG = Logger.getInstance( ChronosSetting.class.getName() );

    private ChronosConfig chronosConfig;

    private ChronosSettingPanel chronosSettingPanel;

    private Icon icon;

    public ChronosSetting()
    {
        chronosConfig = new ChronosConfig();
    }

    public void projectOpened()
    {
    }

    public void projectClosed()
    {

    }

    public String getComponentName()
    {
        return COMPONENT_NAME;
    }

    public void initComponent()
    {
        chronosSettingPanel = new ChronosSettingPanel();
    }

    public void disposeComponent()
    {

    }

    @Nls public String getDisplayName()
    {
        return DISPLAY_NAME;
    }

    public Icon getIcon()
    {
        if( icon == null )
        {
            icon = IconLoader.getIcon( "/org/qi4j/chronos/ui/setting/icon.png" );
        }
        return icon;
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
        if( chronosSettingPanel == null )
        {
            chronosSettingPanel = new ChronosSettingPanel();
        }

        return chronosSettingPanel;
    }

    public boolean isModified()
    {
        //TODO bp. auto save?
        return true;
    }

    public void apply() throws ConfigurationException
    {
        chronosSettingPanel.updateChronosConfig( chronosConfig );
    }

    public void reset()
    {
        chronosSettingPanel.readChronosConfig( chronosConfig );
    }

    public void disposeUIResources()
    {

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
