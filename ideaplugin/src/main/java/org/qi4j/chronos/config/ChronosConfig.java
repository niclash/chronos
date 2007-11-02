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
package org.qi4j.chronos.config;

import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.qi4j.chronos.util.AttributeUtil;

public class ChronosConfig implements JDOMExternalizable
{
    private final static String QI4J_SESSION_IP = "qi4j_session_ip";
    private final static String QI4J_SESSION_PORT = "qi4j_session_port";
    private final static String LOGINID = "loginId";
    private final static String ACCOUNT_NAME = "accountName";
    private final static String PROJECT_NAME = "projectName";
    private final static String IS_PROMPT_UP_LOGIN_DIALOG = "isPromptUpLoginOnStartup";

    private String qi4jSessionIp;
    private int qi4jSessionPort;
    private String loginId;
    private String accountName;
    private String projectName;

    private boolean isPromptUpLoginOnStartup;

    public void readExternal( Element element ) throws InvalidDataException
    {
        qi4jSessionIp = AttributeUtil.getStringAttribute( element, QI4J_SESSION_IP );
        qi4jSessionPort = AttributeUtil.getIntAttribute( element, QI4J_SESSION_PORT, 55000 );
        loginId = AttributeUtil.getStringAttribute( element, LOGINID );
        accountName = AttributeUtil.getStringAttribute( element, ACCOUNT_NAME );
        projectName = AttributeUtil.getStringAttribute( element, PROJECT_NAME );

        isPromptUpLoginOnStartup = AttributeUtil.getBooleanAttribute( element, IS_PROMPT_UP_LOGIN_DIALOG, true );
    }

    public void writeExternal( Element element ) throws WriteExternalException
    {
        AttributeUtil.setAttribute( element, QI4J_SESSION_IP, qi4jSessionIp );
        AttributeUtil.setAttribute( element, QI4J_SESSION_PORT, qi4jSessionPort );
        AttributeUtil.setAttribute( element, LOGINID, loginId );
        AttributeUtil.setAttribute( element, ACCOUNT_NAME, accountName );
        AttributeUtil.setAttribute( element, PROJECT_NAME, projectName );
        AttributeUtil.setAttribute( element, IS_PROMPT_UP_LOGIN_DIALOG, isPromptUpLoginOnStartup );
    }

    public String getQi4jSessionIp()
    {
        return qi4jSessionIp;
    }

    public void setQi4jSessionIp( String qi4jSessionIp )
    {
        this.qi4jSessionIp = qi4jSessionIp;
    }

    public int getQi4jSessionPort()
    {
        return qi4jSessionPort;
    }

    public void setQi4jSessionPort( int qi4jSessionPort )
    {
        this.qi4jSessionPort = qi4jSessionPort;
    }

    public String getLoginId()
    {
        return loginId;
    }

    public void setLoginId( String loginId )
    {
        this.loginId = loginId;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName( String accountName )
    {
        this.accountName = accountName;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName( String projectName )
    {
        this.projectName = projectName;
    }

    public boolean isPromptUpLoginOnStartup()
    {
        return isPromptUpLoginOnStartup;
    }

    public void setPromptLoginDialogOnStartup( boolean promptLoginDialogOnStartup )
    {
        isPromptUpLoginOnStartup = promptLoginDialogOnStartup;
    }
}
