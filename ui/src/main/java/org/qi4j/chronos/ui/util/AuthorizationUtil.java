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
package org.qi4j.chronos.ui.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;

public class AuthorizationUtil
{
    public static boolean isAuthorized( String... systemRoles )
    {
        User user = ChronosSession.get().getUser();

        return isAuthorized( user, systemRoles );
    }

    public static boolean isAuthorized( User user, String... systemRoles )
    {
        String[] userSystemRoles = getSystemRole( user );

        for( String systemRole : systemRoles )
        {
            for( String userSystemRole : userSystemRoles )
            {
                if( systemRole.equals( userSystemRole ) )
                {
                    return true;
                }
            }
        }

        return false;
    }

    public static String[] getSystemRole( User user )
    {
        Set<String> roles = new HashSet<String>();

        getSystemRole( user.systemRoles().iterator(), roles );

        return roles.toArray( new String[roles.size()] );
    }

    private static void getSystemRole( Iterator<SystemRole> iterator, Set<String> roles )
    {
        while( iterator.hasNext() )
        {
            SystemRole next = iterator.next();

            if( !roles.contains( next.name().get() ) )
            {
                roles.add( next.name().get() );
                getSystemRole( next.systemRoles().iterator(), roles );
            }
        }
    }
}
