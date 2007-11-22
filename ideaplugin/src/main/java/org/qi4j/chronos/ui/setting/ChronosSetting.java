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
import org.qi4j.Composite;
import org.qi4j.CompositeBuilder;
import org.qi4j.CompositeBuilderFactory;
import org.qi4j.chronos.config.ChronosConfig;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.composites.ServicesComposite;
import org.qi4j.runtime.Energy4Java;

public class ChronosSetting implements ProjectComponent, Configurable, JDOMExternalizable
{
    //TODO fix icon
    private static final Icon ICON = IconLoader.getIcon( "/org/qi4j/chronos/ui/setting/icon.png" );

    public final static String DISPLAY_NAME = "Chronos Setting";
    public final static String COMPONENT_NAME = "ChronosSetting";

    private ChronosConfig chronosConfig;

    private ChronosSettingPanel chronosSettingPanel;

    private CompositeBuilderFactory factory;
    private Services services;

    private StaffEntityComposite staff;
    private ProjectEntityComposite chronosProject;

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

    public Services getServices()
    {
        return services;
    }

    public StaffEntityComposite getStaff()
    {
        return staff;
    }

    public void initComponent()
    {
        //TODO bp. Initialize the qi4j session here?
        factory = new Energy4Java().newCompositeBuilderFactory();

        CompositeBuilder<ServicesComposite> serviceBuilder = newCompositeBuilder( ServicesComposite.class );

        services = serviceBuilder.newInstance();

        services.initServices();

        //TODO bp. fix this.
        AccountEntityComposite account = services.getAccountService().findAll().get( 0 );

        //TODO bp. where/how to handle userLogin? Let just hard coded it for now.
        staff = (StaffEntityComposite) services.getUserService().getUser( account, "boss", "boss" );

        //TODO bp. assume this project is selected.
        chronosProject = services.getProjectService().findAll().get( 0 );

        chronosSettingPanel = new ChronosSettingPanel( this );
    }

    public <T extends Composite> CompositeBuilder<T> newCompositeBuilder( Class<T> compositeType )
    {
        return factory.newCompositeBuilder( compositeType );
    }

    public <T extends Composite> T newInstance( Class<T> compositeType )
    {
        return factory.newCompositeBuilder( compositeType ).newInstance();
    }

    public ProjectEntityComposite getChronosProject()
    {
        return chronosProject;
    }

    public ProjectAssigneeEntityComposite getProjectAssignee()
    {
        return getServices().getProjectAssigneeService().getProjectAssignee( getChronosProject(), getStaff() );
    }

    public void disposeComponent()
    {
        //TODO bp. release qi4j session
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
