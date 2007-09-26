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
package org.qi4j.chronos.ui.projectrole;

import java.io.Serializable;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;

//TODO bp. we don't need this when ProjectRole can be made serializable
public class ProjectRoleDelegator implements Serializable
{
    private String projectRole;
    private String id;

    public ProjectRoleDelegator( ProjectRoleEntityComposite projectRole )
    {
        this.projectRole = projectRole.getProjectRole();
        this.id = projectRole.getIdentity();
    }

    public String getProjectRole()
    {
        return projectRole;
    }

    public String getId()
    {
        return id;
    }

    public String toString()
    {
        return projectRole;
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

        ProjectRoleDelegator that = (ProjectRoleDelegator) o;

        if( id != null ? !id.equals( that.id ) : that.id != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return ( id != null ? id.hashCode() : 0 );
    }
}
