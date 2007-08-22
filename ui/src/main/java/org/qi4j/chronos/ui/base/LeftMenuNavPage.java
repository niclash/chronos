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
package org.qi4j.chronos.ui.base;

import java.util.Arrays;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.qi4j.chronos.ui.admin.AdminMainMenuBar;
import org.qi4j.chronos.ui.common.menu.MenuBar;
import org.qi4j.chronos.ui.project.RecentProjectMenuBar;

public abstract class LeftMenuNavPage extends UserMenuNavPage
{
    public LeftMenuNavPage()
    {
        //TODO bp. fix me. get the menu bar based on user group.
        MenuBar[] menuBars = getAdminMenuBars();

        ListView menuBarListView = new ListView( "menuBarList", Arrays.asList( menuBars ) )
        {
            @Override
            protected void populateItem( ListItem item )
            {
                MenuBar leftMenuBar = (MenuBar) item.getModelObject();

                item.add( leftMenuBar );
            }
        };

        add( menuBarListView );
    }

    private MenuBar[] getAdminMenuBars()
    {
        return new MenuBar[]{ new AdminMainMenuBar(),
                              new RecentProjectMenuBar() };
    }

    private MenuBar[] getCustomerMenuBars()
    {
        return new MenuBar[]{
            new RecentProjectMenuBar() };
    }


    private MenuBar[] getStaffMenuBars()
    {
        return new MenuBar[]{
            new RecentProjectMenuBar()
        };
    }

}
