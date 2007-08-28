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
package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasSystemRoles;

public class HasSystemRolesMixin implements HasSystemRoles
{
    private List<SystemRole> list;

    public HasSystemRolesMixin()
    {
        list = new ArrayList<SystemRole>();
    }

    public void addSystemRole( SystemRole systemRole )
    {
        list.add( systemRole );
    }

    public void removeSystemRole( SystemRole systemRole )
    {
        list.remove( systemRole );
    }

    public Iterator<SystemRole> systemRoleIterator()
    {
        return list.iterator();
    }
}
