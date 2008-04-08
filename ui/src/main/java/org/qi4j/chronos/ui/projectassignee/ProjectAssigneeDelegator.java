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
package org.qi4j.chronos.ui.projectassignee;

import java.io.Serializable;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;

public class ProjectAssigneeDelegator implements Serializable
{
    private String id;
    private String fullName;

    public ProjectAssigneeDelegator( ProjectAssigneeEntityComposite projectAssignee )
    {
        this.id = projectAssignee.identity().get();

        this.fullName = projectAssignee.staff().get().name().get();
    }

    public String getId()
    {
        return id;
    }

    public String toString()
    {
        return fullName;
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

        ProjectAssigneeDelegator that = (ProjectAssigneeDelegator) o;

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
