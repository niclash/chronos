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

import java.util.List;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.UserService;

public class MockUserServiceMixin implements UserService
{
    private StaffService staffService;

    public MockUserServiceMixin( StaffService staffService )
    {
        this.staffService = staffService;
    }

    public User get( String userId )
    {
        return staffService.get( userId );
    }

    public User getUser( String loginId, String password )
    {
        List<StaffEntityComposite> staffs = staffService.findAll();

        for( StaffEntityComposite staff : staffs )
        {
            Login login = staff.getLogin();

            if( login.getName().equals( loginId ) && login.getPassword().equals( password ) )
            {
                return staff;
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
    }
}
