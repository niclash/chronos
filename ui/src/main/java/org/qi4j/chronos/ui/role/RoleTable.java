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
package org.qi4j.chronos.ui.role;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.RoleEntityComposite;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public class RoleTable extends ActionTable<RoleEntityComposite>
{
    private RoleDataProvider roleDataProvider;

    public RoleTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<RoleEntityComposite> getDetachableDataProvider()
    {
        if( roleDataProvider == null )
        {
            roleDataProvider = new RoleDataProvider();
        }

        return roleDataProvider;
    }

    public void populateItems( Item item, RoleEntityComposite obj )
    {
        final String roleId = obj.getIdentity();

        item.add( new SimpleLink( "roleName", obj.getRole() )
        {
            public void linkClicked()
            {
                RoleDetailPage detailPage = new RoleDetailPage( getBasePage(), roleId );

                setResponsePage( detailPage );
            }
        } );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                RoleEditPage roleEditPage = new RoleEditPage( getBasePage(), roleId );

                setResponsePage( roleEditPage );
            }
        } );
    }

    private BasePage getBasePage()
    {
        return (BasePage) this.getPage();
    }


    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "" );
    }
}
