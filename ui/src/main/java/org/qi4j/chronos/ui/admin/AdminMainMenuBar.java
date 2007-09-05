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
import org.qi4j.chronos.ui.project.ProjectListPage;
import org.qi4j.chronos.ui.projectowner.ProjectOwnerListPage;
import org.qi4j.chronos.ui.projectrole.ProjectRoleListPage;
import org.qi4j.chronos.ui.staff.StaffListPage;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;

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
            new MenuLink( "Home", AdminHomePage.class ),
            new MenuLink( "Account", AccountListPage.class ),
            new MenuLink( "Project Owner", ProjectOwnerListPage.class ),
            new MenuLink( "Project", ProjectListPage.class ),
            new MenuLink( "Staff", StaffListPage.class ),
            new MenuLink( "Project Role", ProjectRoleListPage.class ),
            new MenuLink( "System Role", SystemRoleListPage.class ),
        };
    }
}
