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
import org.qi4j.chronos.util.UiUtil;
import org.qi4j.chronos.util.listener.EventCallback;
import org.qi4j.chronos.util.listener.ListenerHandler;

public class ChronosApp
{
    private CompositeBuilderFactory factory;
    private Services services;

    private ProjectEntityComposite chronosProject;
    private AccountEntityComposite account;

    private ChronosConfig chronosConfig;

    private UserConfig userConfig;

    private ProjectAssigneeEntityComposite projectAssignee;

    private TaskEntityComposite associatedTask;

    private boolean connected = false;

    private ListenerHandler<ChronosAppListener> listenerHandler;

    public ChronosApp( ChronosConfig chronosConfig, CompositeBuilderFactory factory, Services services )
    {
        this.services = services;
        this.factory = factory;
        this.chronosConfig = chronosConfig;

        userConfig = new UserConfig();
        
        listenerHandler = new ListenerHandler<ChronosAppListener>();
    }

    public void addChronosAppListener( ChronosAppListener listener )
    {
        listenerHandler.addListener( listener );
    }

    public void removeChronosAppListener( ChronosAppListener listener )
    {
        listenerHandler.removeListener( listener );
    }

    public void start()
    {
        resetOrConnectToChronosServer();
    }

    public void resetOrConnectToChronosServer()
    {
        if( connected )
        {
            disconnectChronosServer();
        }

        //attempt to establish a connection to chronos server
        boolean isConnected = connectToChronosServer();

        if( !isConnected )
        {
            return;
        }

        //attempt to login to Chronos server
        boolean isLogined = loginToChronosServer();

        if( isLogined )
        {
            connected = true;

            listenerHandler.fireEvent( new EventCallback<ChronosAppListener>()
            {
                public void callback( ChronosAppListener chronosAppListener )
                {
                    chronosAppListener.chronosAppStarted();
                }
            } );
        }
    }

    private void disconnectChronosServer()
    {
        connected = false;

        listenerHandler.fireEvent( new EventCallback<ChronosAppListener>()
        {
            public void callback( ChronosAppListener chronosAppListener )
            {
                chronosAppListener.chronosAppClosed();
            }
        } );
    }

    private boolean loginToChronosServer()
    {
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
                boolean isOk = showUserPassSettingDialog();

                if( !isOk )
                {
                    return false;
                }
            }

            User user = services.getUserService().getUser( account, userConfig.getLoginId(), userConfig.getPassword() );

            if( user == null || !( user instanceof StaffEntityComposite ) )
            {
                UiUtil.showErrorMsgDialog( "Login failed", "Invalid password or loginId" );
                isPromptUpSettingScreen = true;
                continue;
            }

            StaffEntityComposite staff = (StaffEntityComposite) user;

            chronosProject = services.getProjectService().getProjectByName( account, chronosConfig.getProjectName() );

            if( chronosProject == null )
            {
                UiUtil.showErrorMsgDialog( "Failed", "Coudn't associate this IDEA project with Chronos Project. " );
                isPromptUpSettingScreen = true;
                continue;
            }

            projectAssignee = services.getProjectAssigneeService().getProjectAssignee( chronosProject, staff );

            if( projectAssignee == null )
            {
                isPromptUpSettingScreen = true;
                UiUtil.showErrorMsgDialog( "Unassigned Project", "You haven't assigned to this project. Please check with administrator." );
                continue;
            }

            userConfig.writeUserConfig();

            isPromptUpSettingScreen = false;

            return true;
        }
    }

    private boolean connectToChronosServer()
    {
        boolean isAccountEmptyOrSpaces = StringUtil.isEmptyOrSpaces( chronosConfig.getAccountName() );
        boolean isServerIpEmptyorSpaces = StringUtil.isEmptyOrSpaces( chronosConfig.getIp() );

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
                boolean isOk = showChronosServerSettingDialog();

                if( !isOk )
                {
                    return false;
                }
            }

            try
            {
                //TODO bp. connect ChronosServer here
                String accountName = chronosConfig.getAccountName();

                account = services.getAccountService().findAccountByName( accountName );

                if( account == null )
                {
                    isPromptUpSettingScreen = true;
                    UiUtil.showErrorMsgDialog( "Connection Failed", "Invalid account name." );
                    continue;
                }

                return true;
            }
            catch( Exception err )
            {
                err.printStackTrace();
                isPromptUpSettingScreen = true;
            }
        }
    }

    public boolean showChronosServerSettingDialog()
    {
        ChronosServerSettingDialog dialog = new ChronosServerSettingDialog();

        dialog.setIp( chronosConfig.getIp() );
        dialog.setAccountName( chronosConfig.getAccountName() );
        dialog.setPort( chronosConfig.getPort() );

        dialog.show();

        if( dialog.isOK() )
        {
            chronosConfig.setAccountName( dialog.getAccountName() );
            chronosConfig.setIp( dialog.getIp() );
            chronosConfig.setPort( dialog.getPort() );
        }

        return dialog.isOK();
    }

    public boolean showUserPassSettingDialog()
    {
        UserPassSettingDialog dialog = new UserPassSettingDialog();

        dialog.setLoginId( userConfig.getLoginId() );
        dialog.setPassword( userConfig.getPassword() );
        dialog.setProjectName( chronosConfig.getProjectName() );

        dialog.show();

        if( dialog.isOK() )
        {
            userConfig.setLoginId( dialog.getLoginId() );
            userConfig.setPassword( dialog.getPassword() );
            chronosConfig.setProjectName( dialog.getProjectName() );

            userConfig.writeUserConfig();
        }

        return dialog.isOK();
    }

    public void stop()
    {
        disconnectChronosServer();
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