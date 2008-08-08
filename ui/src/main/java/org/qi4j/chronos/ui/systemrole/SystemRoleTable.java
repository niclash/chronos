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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasSystemRoles;
import org.qi4j.chronos.ui.common.action.ActionTable;

public final class SystemRoleTable extends ActionTable<SystemRole>
{
    private static final long serialVersionUID = 1L;

    private static final String[] COLUMN_NAMES = { "Name", "System Role Type" };
    private static final String WICKET_ID_SYSTEM_ROLE_NAME = "systemRoleName";
    private static final String WICKET_ID_SYSTEM_ROLE_TYPE = "systemRoleType";

    public SystemRoleTable( String id, IModel<? extends HasSystemRoles> hasSystemRoles, SystemRoleDataProvider dataProvider )
    {
        super( id, hasSystemRoles, dataProvider, COLUMN_NAMES );
    }

    public void populateItems( Item<SystemRole> item )
    {
        SystemRole systemRole = item.getModelObject();

        item.add( new Label( WICKET_ID_SYSTEM_ROLE_NAME, systemRole.name().get() ) );
        item.add( new Label( WICKET_ID_SYSTEM_ROLE_TYPE, systemRole.systemRoleType().get().toString() ) );
    }
}
