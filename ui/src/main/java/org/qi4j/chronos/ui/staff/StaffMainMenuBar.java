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
package org.qi4j.chronos.ui.staff;

import org.qi4j.chronos.ui.common.menu.MenuBar;
import org.qi4j.chronos.ui.common.menu.MenuItem;
import org.qi4j.chronos.ui.common.menu.PageMenuLink;
import org.qi4j.chronos.ui.customer.CustomerListPage;
import org.qi4j.chronos.ui.project.ProjectListPage;
import org.qi4j.chronos.ui.projectrole.ProjectRoleListPage;
import org.qi4j.chronos.ui.report.ReportMainPage;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;

public class StaffMainMenuBar extends MenuBar
{
    public StaffMainMenuBar()
    {
        super( "Staff - Menu" );
    }

    public MenuItem[] getMenuItemList()
    {
        return new MenuItem[]{
            new PageMenuLink( "Home", StaffHomePage.class ),
            new PageMenuLink( "Customer", CustomerListPage.class ),
            new PageMenuLink( "Project", ProjectListPage.class ),
            new PageMenuLink( "Staff", StaffListPage.class ),
            new PageMenuLink( "Project Role", ProjectRoleListPage.class ),
            new PageMenuLink( "System Role", SystemRoleListPage.class ),
            new PageMenuLink( "Report", ReportMainPage.class ),
        };
    }
}
