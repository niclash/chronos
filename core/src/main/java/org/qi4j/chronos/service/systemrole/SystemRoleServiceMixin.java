/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.service.systemrole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleTypeEnum;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.This;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.service.Activatable;
import org.qi4j.service.Configuration;

public class SystemRoleServiceMixin implements SystemRoleService, Activatable
{
    private @This Configuration<SystemRoleServiceConfiguration> config;

    private @Structure UnitOfWorkFactory factory;

    public List<SystemRole> findAll()
    {
        SystemRole[] systemRoles = new SystemRole[config.configuration().systemRoles().size()];
        return Arrays.asList( config.configuration().systemRoles().toArray( systemRoles ) );
    }

    public void save( SystemRole systemRole )
    {
        validateNotNull( "systemRole", systemRole );

        config.configuration().systemRoles().add( systemRole );
    }

    public void saveAll( Collection<SystemRole> systemRoles )
    {
        validateNotNull( "systemRoles", systemRoles );

        config.configuration().systemRoles().addAll( systemRoles );
    }

    public List<SystemRole> findAllStaffSystemRole()
    {
        List<SystemRole> systemRoles = new ArrayList<SystemRole>();

        for( SystemRole role : config.configuration().systemRoles() )
        {
            if( role.systemRoleType().get().equals( SystemRoleTypeEnum.STAFF ) )
            {
                systemRoles.add( role );
            }
        }
        return systemRoles;
    }

    public List<SystemRole> findAllStaffSystemRole( int first, int count )
    {
        validateNotNull( "first", first );
        validateNotNull( "count", count );

        return findAllStaffSystemRole().subList( first, first + count );
    }

    public int countAllStaffSystemRole()
    {
        return findAllStaffSystemRole().size();
    }

    public SystemRole getSystemRoleByName( String name )
    {
        validateNotNull( "name", name );

        for( SystemRole systemRole : config.configuration().systemRoles() )
        {
            if( name.equals( systemRole.name().get() ) )
            {
                return systemRole;
            }
        }
        return null;
    }

    public void activate() throws Exception
    {
        validateNotNull( "config", config );
        validateNotNull( "factory", factory );
    }

    public void passivate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
