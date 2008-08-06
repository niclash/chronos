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

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasSystemRoles;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.injection.scope.Uses;

@AuthorizeInstantiation( { SystemRole.SYSTEM_ADMIN, SystemRole.ACCOUNT_ADMIN } )
public class SystemRoleListPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasSystemRoles> hasSystemRoles;

    public SystemRoleListPage( @Uses IModel<? extends HasSystemRoles> hasSystemRoles )
    {
        this.hasSystemRoles = hasSystemRoles;
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );

        SystemRoleTable systemRoleTable = new SystemRoleTable( "systemRoleTable", hasSystemRoles, new SystemRoleDataProvider( hasSystemRoles ) );

        systemRoleTable.setActionBarVisible( false );

        add( systemRoleTable );
    }
}
