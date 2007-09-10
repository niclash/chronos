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
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.AdminService;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.UserService;

public class MockUserServiceMixin implements UserService
{
    private StaffService staffService;
    private AdminService adminService;

    public MockUserServiceMixin( StaffService staffService, AdminService adminService )
    {
        this.staffService = staffService;
        this.adminService = adminService;
    }

    public User get( String userId )
    {
        User user = staffService.get( userId );

        if( user != null )
        {
            return user;
        }
        else
        {
            return adminService.get( userId );
        }
    }

    public User getUser( String loginId, String password )
    {
        List<StaffEntityComposite> staffs = staffService.findAll();

        List<AdminEntityComposite> admins = adminService.findAll();

        List<User> users = new ArrayList<User>();

        users.addAll( staffs );
        users.addAll( admins );

        for( User user : users )
        {
            Login login = user.getLogin();

            if( login.getName().equals( loginId ) && login.getPassword().equals( password ) )
            {
                return user;
            }
        }

        return null;
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
        else
        {
            throw new IllegalArgumentException( "Not handled yet" + user.getClass().getName() );
        }
    }
}
