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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.SystemRoleResolver;
import org.qi4j.chronos.ui.common.menu.MenuBar;
import org.qi4j.chronos.ui.common.menu.MenuItem;
import org.qi4j.chronos.ui.common.menu.PageMenuLink;
import org.qi4j.chronos.ui.customer.CustomerListPage;
import org.qi4j.chronos.ui.project.ProjectListPage;
import org.qi4j.chronos.ui.projectrole.ProjectRoleListPage;
import org.qi4j.chronos.ui.report.ReportMainPage;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;

@AuthorizeInstantiation( { SystemRole.ACCOUNT_ADMIN, SystemRole.ACCOUNT_DEVELOPER } )
public class StaffMainMenuBar extends MenuBar
{
    public StaffMainMenuBar()
    {
        super( "Staff - Menu" );
    }

    public MenuItem[] getMenuItemList()
    {
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();

        menuItemList.add( new PageMenuLink( "Home", StaffHomePage.class ) );

        SystemRoleResolver resolver = ChronosSession.get().getSystemRoleResolver();

        if( resolver.isAccountAdmin() )
        {
//            menuItemList.add( new PageMenuLink( "Price Rate Schedule", PriceRateScheduleListPage.class ) );
            menuItemList.add( new PageMenuLink( "Customer", CustomerListPage.class ) );
        }

        menuItemList.add( new PageMenuLink( "Project", ProjectListPage.class ) );

        if( resolver.isAccountAdmin() )
        {
            menuItemList.add( new PageMenuLink( "Staff", StaffListPage.class ) );
            menuItemList.add( new PageMenuLink( "Project Role", ProjectRoleListPage.class ) );
            menuItemList.add( new PageMenuLink( "System Role", SystemRoleListPage.class ) );
            menuItemList.add( new PageMenuLink( "Report", ReportMainPage.class ) );
        }

        return menuItemList.toArray( new MenuItem[menuItemList.size()] );
    }
}
