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
package org.qi4j.chronos.ui.systemrole;

import java.io.Serializable;

public class SystemRoleDelegator implements Serializable
{
    private String roleId;
    private String roleName;

    public SystemRoleDelegator( String roleId, String roleName )
    {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName( String roleName )
    {
        this.roleName = roleName;
    }

    public boolean equals( Object o )
    {
        if( this == o )
        {
            return true;
        }
        if( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        SystemRoleDelegator that = (SystemRoleDelegator) o;

        if( roleId != null ? !roleId.equals( that.roleId ) : that.roleId != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return ( roleId != null ? roleId.hashCode() : 0 );
    }
}
