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
package org.qi4j.chronos.ui.wicket.admin;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.Model;
import static org.qi4j.chronos.model.SystemRole.ACCOUNT_ADMIN;
import org.qi4j.chronos.ui.wicket.admin.account.AccountListPage;
import org.qi4j.chronos.ui.common.menu.MenuGroupPanel;
import org.qi4j.chronos.ui.common.menu.MenuItemPanel;
import org.qi4j.chronos.ui.common.menu.PageMenuItemPanel;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;

@AuthorizeInstantiation( ACCOUNT_ADMIN )
public class AdminMainMenuGroup extends MenuGroupPanel
{
    private static final long serialVersionUID = 1L;

    public AdminMainMenuGroup()
    {
        super( new Model<String>( "Admin - Menu" ) );
    }

    @Override
    public final List<MenuItemPanel> getMenuItemList()
    {
        ArrayList<MenuItemPanel> itemPanels = new ArrayList<MenuItemPanel>();
        itemPanels.add( new PageMenuItemPanel( "Home", AdminHomePage.class ) );
        itemPanels.add( new PageMenuItemPanel( "Account", AccountListPage.class ) );
        itemPanels.add( new PageMenuItemPanel( "System Role", SystemRoleListPage.class ) );
        return itemPanels;
    }
}
