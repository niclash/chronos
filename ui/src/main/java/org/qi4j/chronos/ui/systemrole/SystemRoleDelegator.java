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
import org.qi4j.chronos.model.SystemRoleTypeEnum;
import org.qi4j.chronos.model.SystemRole;


//TODO bp. we dun need this when we have ValueComposite
public class SystemRoleDelegator implements Serializable
{
    private String systemRoleName;

    private SystemRoleTypeEnum systemRoleType;

    public SystemRoleDelegator( SystemRole systemRole )
    {
        this.systemRoleName = systemRole.name().get();
        this.systemRoleType = systemRole.systemRoleType().get();
    }

    public String getSystemRoleName()
    {
        return systemRoleName;
    }

    public SystemRoleTypeEnum getSystemRoleType()
    {
        return systemRoleType;
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

        if( systemRoleName != null ? !systemRoleName.equals( that.systemRoleName ) : that.systemRoleName != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return ( systemRoleName != null ? systemRoleName.hashCode() : 0 );
    }
}
