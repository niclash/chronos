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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.protocol.http.WebResponse;
import org.qi4j.chronos.ui.SystemRoleResolver;
import org.qi4j.chronos.ui.wicket.admin.AdminMainMenuGroup;
import org.qi4j.chronos.ui.common.menu.MenuGroupPanel;
import org.qi4j.chronos.ui.contactperson.ContactPersonMainMenuGroup;
import org.qi4j.chronos.ui.project.RecentProjectMenuGroup;
import org.qi4j.chronos.ui.staff.StaffMainMenuGroup;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;

public abstract class LeftMenuNavPage extends TopMenuNavPage
{
    private static final long serialVersionUID = 1L;

    public LeftMenuNavPage()
    {
        ChronosSession session = ChronosSession.get();

        MenuGroupPanel[] menuBars = null;

        SystemRoleResolver systemRoleResolver = session.getSystemRoleResolver();

        if( systemRoleResolver.isAdmin() )
        {
            menuBars = getAdminMenuBars();
        }
        else if( systemRoleResolver.isStaff() )
        {
            menuBars = getStaffMenuBars();
        }
        else if( systemRoleResolver.isContactPerson() )
        {
            menuBars = getContactPersonMenuBars();
        }

        ListView menuBarListView = new ListView( "menuBarList", Arrays.asList( menuBars ) )
        {
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
