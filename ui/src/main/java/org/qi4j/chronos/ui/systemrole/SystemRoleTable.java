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

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.service.systemrole.SystemRoleService;

public class SystemRoleTable extends ActionTable<SystemRole, String>
{
    private StaffSystemRoleDataProvider dataProvider;

    private SystemRoleService systemRoleService;

    public SystemRoleTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<SystemRole, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new StaffSystemRoleDataProvider();
        }

        return dataProvider;
    }

    public void populateItems( Item item, SystemRole obj )
    {
        item.add( new Label( "systemRoleName", obj.name().get() ) );
        item.add( new Label( "systemRoleType", obj.systemRoleType().get().toString() ) );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "System Role Type" );
    }
}
