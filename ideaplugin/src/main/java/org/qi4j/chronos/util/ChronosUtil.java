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
package org.qi4j.chronos.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import java.io.File;
import java.util.Date;

public final class ChronosUtil
{
    public final static String CHRONOS_DIR_NAME = ".chronos";

    public static String getUserHomeDir()
    {
        return System.getProperties().getProperty( "user.home" );
    }

    public static File getChronosDir()
    {
        String chronosHome = getUserHomeDir() + File.separator + CHRONOS_DIR_NAME;

        File file = new File( chronosHome );

        if( !file.exists() || !file.isDirectory() )
        {
            file.mkdir();
        }

        return file;
    }

//    public static ChronosSetting getChronosSetting( Project project )
//    {
//        return project.getComponent( ChronosSetting.class );
//    }
//
//    public static ChronosSetting getChronosSetting( AnActionEvent e )
//    {
//        return getChronosSetting( getProject( e ) );
//    }
//
//    public static ChronosSetting getChronosSetting()
//    {
//        return getChronosSetting( getProject( DataManager.getInstance().getDataContext() ) );
//    }

    public static Project getProject( AnActionEvent e )
    {
        return ( (Project) e.getDataContext().getData( DataConstants.PROJECT ) );
    }

    public static Project getProject( DataContext dataContext )
    {
        return ( (Project) dataContext.getData( DataConstants.PROJECT ) );
    }

    public static Date getCurrentDate()
    {
        //TODO bp. get it from where? server? need to ensure all have consistent time?
        return new Date();
    }


}
