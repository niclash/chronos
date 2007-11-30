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

import com.intellij.openapi.util.text.StringUtil;
import org.qi4j.Composite;
import org.qi4j.CompositeBuilder;
import org.qi4j.CompositeBuilderFactory;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.composites.ServicesComposite;
import org.qi4j.runtime.Energy4Java;

public class ChronosApp
{
    private CompositeBuilderFactory factory;
    private Services services;

    private ProjectEntityComposite chronosProject;
    private AccountEntityComposite account;

    private ChronosConfig chronosConfig;

    private ProjectAssigneeEntityComposite projectAssignee;

    private TaskEntityComposite associatedTask;

    public ChronosApp( ChronosConfig chronosConfig )
    {
        this.chronosConfig = chronosConfig;
    }

    public void start()
    {
        //attempt to establish a connection to chronos server
        boolean isConnected = connectToChronosServer();

        if( !isConnected )
        {
            return;
        }

        //attempt to login to Chronos server
        boolean isLogined = loginToChronosServer();

        if( !isLogined )
        {
            return;
        }

        //TODO fire connected event
    }

    private boolean loginToChronosServer()
    {
        UserConfig userConfig = new UserConfig();

        userConfig.readUserConfig();

        boolean isLoginIdEmptyOrSpaces = StringUtil.isEmptyOrSpaces( userConfig.getLoginId() );
        boolean isPasswordEmptyOrSpaces = StringUtil.isEmptyOrSpaces( userConfig.getPassword() );
        boolean isProjectEmptyOrSpaces = StringUtil.isEmptyOrSpaces( chronosConfig.getProjectName() );

        boolean isPromptUpSettingScreen = false;

        if( isLoginIdEmptyOrSpaces || isPasswordEmptyOrSpaces || isProjectEmptyOrSpaces )
        {
            isPromptUpSettingScreen = true;
        }

        while( true )
        {
            if( isPromptUpSettingScreen )
            {
                //TODO pop up screen here with userConfig
                //TODO check if user clicked cancel button,  return false;
                boolean isCancelled = false;

                if( isCancelled )
                {
                    return false;
                }
            }

            User user = services.getUserService().getUser( account, userConfig.getLoginId(), userConfig.getPassword() );

            if( user == null || !( user instanceof StaffEntityComposite ) )
            {
                //TODO prompt error dialog, invalid loginId or password
                //weird, pop up setting screen again
                continue;
            }

            StaffEntityComposite staff = (StaffEntityComposite) user;

            chronosProject = services.getProjectService().getProjectByName( account, chronosConfig.getProjectName() );

            if( chronosProject == null )
            {
                //TODO prompt error dialog, invalid projectName
                continue;
            }

            projectAssignee = services.getProjectAssigneeService().getProjectAssignee( chronosProject, staff );

            if( projectAssignee == null )
            {
                //TODO prompt error dialog, this project is not assigned.
                continue;
            }

            return true;
        }
    }

    private boolean connectToChronosServer()
    {
        boolean isAccountEmptyOrSpaces = StringUtil.isEmptyOrSpaces( chronosConfig.getAccountName() );
        boolean isServerIpEmptyorSpaces = StringUtil.isEmptyOrSpaces( chronosConfig.getQi4jSessionIp() );

        //assuming this project doesn't need to associate Chronos project
        if( isAccountEmptyOrSpaces && isServerIpEmptyorSpaces )
        {
            System.err.println( "This project has no sign of associating with a Chronos project." );
            return false;
        }

        boolean isPromptUpSettingScreen = false;

        if( isAccountEmptyOrSpaces || isServerIpEmptyorSpaces )
        {
            isPromptUpSettingScreen = true;
        }

        while( true )
        {
            if( isPromptUpSettingScreen )
            {
                //TODO pop up dialog here
                //TODO check if user clicked cancel button return false;

                boolean isCancelled = false;

                if( isCancelled )
                {
                    return false;
                }
            }

            try
            {
                //TODO bp. fix it when Qi4j session or storage is ready
                factory = new Energy4Java().newCompositeBuilderFactory();

                CompositeBuilder<ServicesComposite> serviceBuilder = newCompositeBuilder( ServicesComposite.class );

                services = serviceBuilder.newInstance();

                services.initServices();

                String accountName = chronosConfig.getAccountName();

                account = services.getAccountService().findAccountByName( accountName );

                if( account == null )
                {
                    //TODO prompup setting dialog and

                    //TODO  if user clicked cancel button, return false and disconnect the connection
                }

                return true;
            }
            catch( Exception err )
            {
                isPromptUpSettingScreen = true;
            }
        }
    }

    public void stop()
    {

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

    public StaffEntityComposite getStaff()
    {
        return projectAssignee.getStaff();
    }

    public ProjectAssigneeEntityComposite getProjectAssignee()
    {
        return projectAssignee;
    }

    public Services getServices()
    {
        return services;
    }

    public TaskEntityComposite getAssociatedTask()
    {
        return associatedTask;
    }
}
