/*  Copyright 2008 Edward Yakop.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
* implied.
*
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.qi4j.chronos.domain.model.project;

import java.io.Serializable;
import static org.qi4j.api.util.NullArgumentException.*;
import org.qi4j.chronos.domain.model.ValueObject;

public class ProjectId
    implements ValueObject<ProjectId>, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String idString;

    public ProjectId( String projectIdString )
        throws IllegalArgumentException
    {
        validateNotNull( "projectIdString", projectIdString );
        idString = projectIdString;
    }

    public String idString()
    {
        return idString;
    }

    public boolean sameValueAs( ProjectId other )
    {
        return other != null && idString.equals( other.idString );
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

        ProjectId projectId = (ProjectId) o;

        return idString.equals( projectId.idString );
    }

    public int hashCode()
    {
        return idString.hashCode();
    }
}
