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
package org.qi4j.chronos.ui.systemrole;

import java.util.List;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.SystemRoleService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public class StaffSystemRoleDataProvider extends AbstractSortableDataProvider<SystemRoleEntityComposite>
{
    private SystemRoleService getSystemRoleService()
    {
        return ChronosWebApp.getServices().getSystemRoleService();
    }

    public String getId( SystemRoleEntityComposite systemRoleEntityComposite )
    {
        return systemRoleEntityComposite.getIdentity();
    }

    public SystemRoleEntityComposite load( String id )
    {
        return getSystemRoleService().get( id );
    }

    public List<SystemRoleEntityComposite> dataList( int first, int count )
    {
        return getSystemRoleService().findAllStaffSystemRole( first, count );
    }

    public int size()
    {
        return getSystemRoleService().countAllStaffSystemRole();
    }
}