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

import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.qi4j.chronos.util.AttributeUtil;

public class ChronosConfig implements JDOMExternalizable
{
    private final static String IP = "ip";
    private final static String PORT = "port";
    private final static String ACCOUNT_NAME = "accountName";
    private final static String PROJECT_NAME = "projectName";

    private String ip;
    private int port;

    private String accountName;
    private String projectName;

    public void readExternal( Element element ) throws InvalidDataException
    {
        ip = AttributeUtil.getStringAttribute( element, IP );
        port = AttributeUtil.getIntAttribute( element, PORT, 55000 );
        accountName = AttributeUtil.getStringAttribute( element, ACCOUNT_NAME );
        projectName = AttributeUtil.getStringAttribute( element, PROJECT_NAME );
    }

    public void writeExternal( Element element ) throws WriteExternalException
    {
        AttributeUtil.setAttribute( element, IP, ip );
        AttributeUtil.setAttribute( element, PORT, port );
        AttributeUtil.setAttribute( element, ACCOUNT_NAME, accountName );
        AttributeUtil.setAttribute( element, PROJECT_NAME, projectName );
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp( String ip )
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort( int port )
    {
        this.port = port;
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
}
