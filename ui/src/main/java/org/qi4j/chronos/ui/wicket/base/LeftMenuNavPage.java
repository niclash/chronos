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
package org.qi4j.chronos.ui.wicket.base;

import java.util.Arrays;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.protocol.http.WebResponse;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.common.menu.MenuGroupPanel;
import org.qi4j.chronos.ui.contactperson.ContactPersonMainMenuGroup;
import org.qi4j.chronos.ui.project.RecentProjectMenuGroup;
import org.qi4j.chronos.ui.staff.StaffMainMenuGroup;
import org.qi4j.chronos.ui.wicket.admin.AdminMainMenuGroup;

public abstract class LeftMenuNavPage extends TopMenuNavPage
{
    private static final long serialVersionUID = 1L;

    public LeftMenuNavPage()
    {
        AuthenticatedWebSession authenticatedSession = (AuthenticatedWebSession) getSession();
        Roles roles = authenticatedSession.getRoles();

        MenuGroupPanel[] menuBars = null;

        if( roles.contains( SystemRole.ACCOUNT_ADMIN ) )
        {
            menuBars = getAdminMenuBars();
        }
        else if( roles.contains( SystemRole.STAFF ) )
        {
            menuBars = getStaffMenuBars();
        }
        else if( roles.contains( SystemRole.CONTACT_PERSON ) )
        {
            menuBars = getContactPersonMenuBars();
        }

        ListView menuBarListView = new ListView( "menuBarList", Arrays.asList( menuBars ) )
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem( ListItem item )
            {
                MenuGroupPanel leftMenuBar = (MenuGroupPanel) item.getModelObject();

                item.add( leftMenuBar );
            }
        };

        add( menuBarListView );
    }

    private MenuGroupPanel[] getAdminMenuBars()
    {
        return new MenuGroupPanel[]{ new AdminMainMenuGroup() };
    }

    private MenuGroupPanel[] getContactPersonMenuBars()
    {
        return new MenuGroupPanel[]{ new ContactPersonMainMenuGroup(), createRecentProjectMenuBar() };
    }

    private MenuGroupPanel[] getStaffMenuBars()
    {
        return new MenuGroupPanel[]{ new StaffMainMenuGroup(), createRecentProjectMenuBar() };
    }

    private MenuGroupPanel createRecentProjectMenuBar()
    {
        return new RecentProjectMenuGroup();
    }

    @Override public boolean isVersioned()
    {
        return false;
    }

    @Override protected void setHeaders( WebResponse response )
    {
        response.setHeader( "Pragma", "no-cache" );
        response.setHeader( "Cache-Control", "no-cache, max-age=0, must-revalidate, no-store" );
    }
}
