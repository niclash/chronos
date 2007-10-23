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
package org.qi4j.chronos.ui.admin;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.account.AccountListPage;
import org.qi4j.chronos.ui.common.menu.MenuBar;
import org.qi4j.chronos.ui.common.menu.MenuItem;
import org.qi4j.chronos.ui.common.menu.PageMenuLink;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public class AdminMainMenuBar extends MenuBar
{
    public AdminMainMenuBar()
    {
        super( "Admin - Menu" );
    }

    public MenuItem[] getMenuItemList()
    {
        return new MenuItem[]{
            new PageMenuLink( "Home", AdminHomePage.class ),
            new PageMenuLink( "Account", AccountListPage.class ),
            new PageMenuLink( "System Role", SystemRoleListPage.class ),
        };
    }
}
