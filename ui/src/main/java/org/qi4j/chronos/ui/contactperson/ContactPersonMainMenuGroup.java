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
package org.qi4j.chronos.ui.contactperson;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.Model;
import static org.qi4j.chronos.model.SystemRole.CONTACT_PERSON;
import org.qi4j.chronos.ui.common.menu.MenuGroupPanel;
import org.qi4j.chronos.ui.common.menu.MenuItemPanel;
import org.qi4j.chronos.ui.common.menu.PageMenuItemPanel;
import org.qi4j.chronos.ui.project.ProjectListPage;

@AuthorizeInstantiation( CONTACT_PERSON )
public class ContactPersonMainMenuGroup extends MenuGroupPanel
{
    private static final long serialVersionUID = 1L;

    public ContactPersonMainMenuGroup()
    {
        super( new Model<String>( "Contact Person - Menu" ) );
    }

    @Override
    public final List<MenuItemPanel> getMenuItemList()
    {
        ArrayList<MenuItemPanel> menuItems = new ArrayList<MenuItemPanel>();
        menuItems.add( new PageMenuItemPanel( "Home", ContactPersonHomePage.class ) );
        menuItems.add( new PageMenuItemPanel( "Project", ProjectListPage.class ) );
        return menuItems;
    }
}
