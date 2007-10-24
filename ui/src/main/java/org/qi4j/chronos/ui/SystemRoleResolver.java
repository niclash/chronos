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
package org.qi4j.chronos.ui;

import java.io.Serializable;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.util.AuthorizationUtil;

public class SystemRoleResolver implements Serializable
{
    private boolean isAdmin;
    private boolean isStaff;
    private boolean isContactPerson;

    private boolean isAccountAdmin;
    private boolean isAccountDeveloper;

    private Roles roles;

    public SystemRoleResolver( User user )
    {
        if( user instanceof Admin )
        {
            isAdmin = true;
        }
        else if( user instanceof Staff )
        {
            isStaff = true;
        }
        else if( user instanceof ContactPerson )
        {
            isContactPerson = true;
        }

        isAccountAdmin = AuthorizationUtil.isAuthorized( user, SystemRole.ACCOUNT_ADMIN );
        isAccountDeveloper = AuthorizationUtil.isAuthorized( user, SystemRole.ACCOUNT_DEVELOPER );

        String[] systemRoles = AuthorizationUtil.getSystemRole( user );

        roles = new Roles( systemRoles );
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public boolean isStaff()
    {
        return isStaff;
    }

    public boolean isContactPerson()
    {
        return isContactPerson;
    }

    public boolean isAccountAdmin()
    {
        return isAccountAdmin;
    }

    public boolean isAccountDeveloper()
    {
        return isAccountDeveloper;
    }

    public Roles getRoles()
    {
        return roles;
    }
}
