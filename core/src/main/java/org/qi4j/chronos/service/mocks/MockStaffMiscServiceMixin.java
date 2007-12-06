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

import java.util.Collection;
import java.util.List;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.composite.scope.ThisCompositeAs;

public abstract class MockStaffMiscServiceMixin implements StaffService
{
    @ThisCompositeAs private StaffService staffService;

    public List<StaffEntityComposite> getRecentActiveStaff( AccountEntityComposite account, FindFilter findFilter )
    {
        return staffService.findAll( account );
    }

    public void enableLogin( boolean enabled, Collection<StaffEntityComposite> staffs )
    {
        for( StaffEntityComposite staff : staffs )
        {
            staff.getLogin().setEnabled( enabled );

            staffService.update( staff );
        }
    }
}
