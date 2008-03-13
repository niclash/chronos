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
import org.qi4j.association.SetAssociation;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.service.AdminService;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.UserService;
import org.qi4j.composite.scope.Uses;

public class MockUserServiceMixin implements UserService
{
    @Uses private StaffService staffService;
    @Uses private AdminService adminService;
    @Uses private ContactPersonService contactPersonService;
    @Uses private CustomerService customerService;

    public MockUserServiceMixin()
    {
    }

    public User get( String userId )
    {
        User user = staffService.get( userId );

        if( user != null )
        {
            return user;
        }

        user = adminService.get( userId );

        if( user != null )
        {
            return user;
        }

        return contactPersonService.get( userId );
    }

    public User getUser( String loginId, String password )
    {
        List<AdminEntityComposite> admins = adminService.findAll();

        return loopUser( admins, loginId, password );
    }

    private User loopUser( List<? extends User> users, String loginId, String password )
    {
        for( User user : users )
        {
            Login login = user.login().get();

            if( login.name().get().equals( loginId ) && login.password().get().equals( password ) )
            {
                return user;
            }
        }

        return null;
    }

    public User getUser( AccountEntityComposite account, String loginId, String password )
    {
        List<User> users = new ArrayList<User>();

        users.addAll( staffService.findAll( account ) );

        List<CustomerEntityComposite> customers = customerService.findAll( account );

        for( CustomerEntityComposite customer : customers )
        {
            users.addAll( contactPersonService.findAll( customer ) );
        }

        return loopUser( users, loginId, password );
    }

    public void update( User user )
    {
        if( user instanceof StaffEntityComposite )
        {
            staffService.update( (StaffEntityComposite) user );
        }
        else if( user instanceof AdminEntityComposite )
        {
            adminService.update( (AdminEntityComposite) user );
        }
        else if( user instanceof ContactPersonEntityComposite )
        {
            contactPersonService.update( (ContactPersonEntityComposite) user );
        }
        else
        {
            throw new IllegalArgumentException( "Not handled yet" + user.getClass().getName() );
        }
    }

    public boolean hasThisSystemRole( User user, String systemRoleName )
    {
        SetAssociation<SystemRoleComposite> userSystemRoles = user.systemRoles();

        //TODO bp. loop system role group
        for( SystemRoleComposite systemRole : userSystemRoles )
        {
            if( systemRole.name().get().equals( systemRoleName ) )
            {
                return true;
            }
        }

        return false;
    }
}
