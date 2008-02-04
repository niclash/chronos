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
package org.qi4j.chronos.service.mocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.qi4j.chronos.model.SystemRoleType;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.service.SystemRoleService;

public class MockSystemRoleMiscServiceMixin implements SystemRoleService
{
    private List<SystemRoleComposite> list;

    public List<SystemRoleComposite> findAll()
    {
        return Collections.unmodifiableList( list );
    }

    public void save( SystemRoleComposite systemRole )
    {
        list.add( systemRole );
    }

    public MockSystemRoleMiscServiceMixin()
    {
        list = new ArrayList<SystemRoleComposite>();
    }

    public List<SystemRoleComposite> findAllStaffSystemRole( int first, int count )
    {
        return findAllStaffSystemRole().subList( first, count + first );
    }

    public List<SystemRoleComposite> findAllStaffSystemRole()
    {
        List<SystemRoleComposite> staffSystemRoleList = new ArrayList<SystemRoleComposite>();

        for( SystemRoleComposite systemRole : list )
        {
            if( systemRole.systemRoleType().get().equals( SystemRoleType.STAFF ) )
            {
                staffSystemRoleList.add( systemRole );
            }
        }

        return staffSystemRoleList;
    }

    public int countAllStaffSystemRole()
    {
        return findAllStaffSystemRole().size();
    }

    public SystemRoleComposite getSystemRoleByName( String name )
    {
        for( SystemRoleComposite systemRole : list )
        {
            if( systemRole.name().get().equals( name ) )
            {
                return systemRole;
            }
        }

        return null;
    }
}
