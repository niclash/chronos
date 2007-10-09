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
import java.util.List;
import org.qi4j.api.annotation.scope.ThisAs;
import org.qi4j.chronos.model.SystemRoleType;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.service.SystemRoleMiscService;

public class MockSystemRoleMiscServiceMixin implements SystemRoleMiscService
{
    @ThisAs private EntityService<SystemRoleEntityComposite> systemRoleService;

    public MockSystemRoleMiscServiceMixin()
    {

    }

    public List<SystemRoleEntityComposite> findAllStaffSystemRole( int first, int count )
    {
        return findAllStaffSystemRole().subList( first, count + first );
    }

    public List<SystemRoleEntityComposite> findAllStaffSystemRole()
    {
        List<SystemRoleEntityComposite> allList = systemRoleService.findAll();

        List<SystemRoleEntityComposite> staffSystemRoleList = new ArrayList<SystemRoleEntityComposite>();

        for( SystemRoleEntityComposite systemRole : allList )
        {
            if( systemRole.getSystemRoleType() == SystemRoleType.STAFF )
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

    public SystemRoleEntityComposite getSystemRoleByName( String name )
    {
        List<SystemRoleEntityComposite> allList = systemRoleService.findAll();

        for( SystemRoleEntityComposite systemRole : allList )
        {
            if( systemRole.getName().equals( name ) )
            {
                return systemRole;
            }
        }

        return null;
    }
}
