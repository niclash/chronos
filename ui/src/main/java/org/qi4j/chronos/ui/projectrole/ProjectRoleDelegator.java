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
import org.qi4j.chronos.model.composites.ProjectRoleComposite;

//TODO bp. we don't need this when ProjectRole can be made serializable
public class ProjectRoleDelegator implements Serializable
{
    private String name;

    public ProjectRoleDelegator( ProjectRoleComposite name )
    {
        this.name = name.name().get();
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return name;
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

        if( name != null ? !name.equals( that.name ) : that.name != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return ( name != null ? name.hashCode() : 0 );
    }
}
