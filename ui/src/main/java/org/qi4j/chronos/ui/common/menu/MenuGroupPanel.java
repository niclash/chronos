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
package org.qi4j.chronos.ui.common.menu;

import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class MenuGroupPanel extends Panel
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_TITLE_LABEL = "titleLabel";
    private static final String WICKET_ID_MENU_BAR = "menuBar";
    private static final String WICKET_ID_MENU_LIST = "menuList";

    private static final String ATTRIBUTE_CLASS = "class";

    public MenuGroupPanel( IModel aTitleModel )
    {
        super( WICKET_ID_MENU_BAR );

        add( new Label( WICKET_ID_TITLE_LABEL, aTitleModel ) );

        List<MenuItemPanel> menuItemList = getMenuItemList();
        ItemPanel listView = new ItemPanel( menuItemList );
        add( listView );
    }

    public abstract List<MenuItemPanel> getMenuItemList();

    private static class ItemPanel extends ListView<MenuItemPanel>
    {
        private static final long serialVersionUID = 1L;

        public ItemPanel( List<MenuItemPanel> menuItemList )
        {
            super( WICKET_ID_MENU_LIST, menuItemList );
        }

        @Override
        protected void populateItem( ListItem<MenuItemPanel> item )
        {
            MenuItemPanel panel = item.getModelObject();

            if( item.getIndex() == 0 )
            {
                panel.add( new AttributeModifier( ATTRIBUTE_CLASS, true, new Model<String>( "first" ) ) );
            }

            item.add( panel );
        }
    }
}

