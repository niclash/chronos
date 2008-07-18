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
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.Model;
import static org.qi4j.chronos.model.SystemRole.ACCOUNT_ADMIN;
import static org.qi4j.chronos.model.SystemRole.ACCOUNT_DEVELOPER;
import org.qi4j.chronos.ui.common.menu.MenuGroupPanel;
import org.qi4j.chronos.ui.common.menu.MenuItemPanel;
import org.qi4j.chronos.ui.common.menu.PageMenuItemPanel;
import org.qi4j.chronos.ui.customer.CustomerListPage;
import org.qi4j.chronos.ui.project.ProjectListPage;
import org.qi4j.chronos.ui.projectrole.ProjectRoleListPage;
import org.qi4j.chronos.ui.report.ReportMainPage;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;

@AuthorizeInstantiation( { ACCOUNT_ADMIN, ACCOUNT_DEVELOPER } )
public class StaffMainMenuGroup extends MenuGroupPanel
{
    private static final long serialVersionUID = 1L;

    public StaffMainMenuGroup()
    {
        super( new Model<String>( "Staff - Menu" ) );
    }

    @Override public List<MenuItemPanel> getMenuItemList()
    {
        List<MenuItemPanel> menuItemList = new ArrayList<MenuItemPanel>();

        menuItemList.add( new PageMenuItemPanel( "Home", StaffHomePage.class ) );

        AuthenticatedWebSession session = (AuthenticatedWebSession) getSession();
        Roles roles = session.getRoles();
        boolean isAccountAdmin = hasRole( roles, ACCOUNT_ADMIN );

        if( isAccountAdmin )
        {
//            menuItemList.add( new PageMenuLink( "Price Rate Schedule", PriceRateScheduleListPage.class ) );
            menuItemList.add( new PageMenuItemPanel( "Customer", CustomerListPage.class ) );
        }

        menuItemList.add( new PageMenuItemPanel( "Project", ProjectListPage.class ) );

        if( isAccountAdmin )
        {
            menuItemList.add( new PageMenuItemPanel( "Staff", StaffListPage.class ) );
            menuItemList.add( new PageMenuItemPanel( "Project Role", ProjectRoleListPage.class ) );
            menuItemList.add( new PageMenuItemPanel( "System Role", SystemRoleListPage.class ) );
            menuItemList.add( new PageMenuItemPanel( "Report", ReportMainPage.class ) );
        }

        return menuItemList;
    }

    private boolean hasRole( Roles roles, String roleToFind )
    {
        return roles.contains( roleToFind );
    }
}
