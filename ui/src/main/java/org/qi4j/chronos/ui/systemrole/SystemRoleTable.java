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
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.action.ActionTable;

public class SystemRoleTable extends ActionTable<SystemRoleEntityComposite>
{
    private SimpleDataProvider<SystemRoleEntityComposite> dataProvider;

    public SystemRoleTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<SystemRoleEntityComposite> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new SimpleDataProvider<SystemRoleEntityComposite>()
            {
                public EntityService<SystemRoleEntityComposite> getEntityService()
                {
                    return ChronosWebApp.getServices().getSystemRoleService();
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, SystemRoleEntityComposite obj )
    {
        item.add( new Label( "systemRoleName", obj.getName() ) );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name" );
    }
}
