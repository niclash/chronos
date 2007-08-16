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

import org.qi4j.chronos.ui.account.AccountListPage;
import org.qi4j.chronos.ui.common.menu.MenuBar;
import org.qi4j.chronos.ui.common.menu.MenuItem;
import org.qi4j.chronos.ui.common.menu.MenuLink;
import org.qi4j.chronos.ui.customer.CustomerListPage;
import org.qi4j.chronos.ui.staff.StaffListPage;

public class AdminMainMenuBar extends MenuBar
{
    public AdminMainMenuBar()
    {
        super( "Main" );
    }

    public MenuItem[] getMenuItemList()
    {
        //TODO bp. fix this
        return new MenuItem[]{
            new MenuLink( "Account", AccountListPage.class ),
            new MenuLink( "Customer", CustomerListPage.class ),
            new MenuLink( "Staff", StaffListPage.class ),
        };
    }
}
