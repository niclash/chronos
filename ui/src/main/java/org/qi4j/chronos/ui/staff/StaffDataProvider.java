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
package org.qi4j.chronos.ui.staff;

import java.util.List;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class StaffDataProvider extends AbstractSortableDataProvider<StaffEntityComposite>
{
    public String getId( StaffEntityComposite staffEntityComposite )
    {
        return staffEntityComposite.getIdentity();
    }

    private StaffService getStaffService()
    {
        return ChronosWebApp.getServices().getStaffService();
    }

    public StaffEntityComposite load( String id )
    {
        return getStaffService().get( id );
    }

    public List<StaffEntityComposite> dataList( int first, int count )
    {
        return getStaffService().findAll( getAccountEntityComposite(), new FindFilter( first, count ) );
    }

    private AccountEntityComposite getAccountEntityComposite()
    {
        return ChronosWebApp.getServices().getAccountService().get( getAccountId() );
    }

    public int size()
    {
        return getStaffService().countAll( getAccountEntityComposite() );
    }

    public abstract String getAccountId();
}
