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

import com.intellij.openapi.util.PasswordUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.qi4j.chronos.util.ChronosUtil;

public class UserConfig
{
    private final static String USER_CONFIG_FILE = "userConfig.properties";

    private final static String LOGINID = "loginId";
    private final static String PASSWORD = "password";

    private String loginId;
    private String password;

    public String getLoginId()
    {
        return loginId;
    }

    public void setLoginId( String loginId )
    {
        this.loginId = loginId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public void readUserConfig()
    {
        FileInputStream fileInputStream = null;

        try
        {
            Properties properties = new Properties();

            File chronosDir = ChronosUtil.getChronosDir();

            File userConfigFile = new File( chronosDir, USER_CONFIG_FILE );

            if( !userConfigFile.exists() )
            {
                return;
            }

            fileInputStream = new FileInputStream( userConfigFile );

            properties.load( fileInputStream );

            loginId = properties.getProperty( LOGINID );
            password = PasswordUtil.decodePassword( properties.getProperty( PASSWORD ) );
        }
        catch( Exception err )
        {
            //TODO bp. handle err
            err.printStackTrace();
        }
        finally
        {
            if( fileInputStream != null )
            {
                try
                {
                    fileInputStream.close();
                }
                catch( IOException ioErr )
                {
                    //ignore it
                }
            }
        }
    }

    public void writeUserConfig()
    {
        FileOutputStream fileOutputStream = null;

        try
        {
            Properties properties = new Properties();

            properties.setProperty( LOGINID, loginId );
            properties.setProperty( PASSWORD, PasswordUtil.encodePassword( password ) );

            File chronosDir = ChronosUtil.getChronosDir();

            fileOutputStream = new FileOutputStream( new File( chronosDir, USER_CONFIG_FILE ) );

            properties.store( fileOutputStream, null );
        }
        catch( Exception err )
        {
            //TODO bp. handle exception
            err.printStackTrace();
        }
        finally
        {
            if( fileOutputStream != null )
            {
                try
                {
                    fileOutputStream.close();
                }
                catch( IOException ioErr )
                {
                    //ignore it
                }
            }
        }
    }
}
