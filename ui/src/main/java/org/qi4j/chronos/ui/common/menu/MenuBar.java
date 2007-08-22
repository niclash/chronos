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

import java.util.Arrays;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public abstract class MenuBar extends Panel
{
    private static final long serialVersionUID = 1L;

    private ListView listView;

    private String title;

    public MenuBar( String title )
    {
        super( "menuBar" );

        this.title = title;

        initComponents();
    }

    private void initComponents()
    {
        add( new Label( "titleLabel", title ) );

        final MenuItem[] menuItems = getMenuItemList();

        listView = new ListView( "menuList", Arrays.asList( menuItems ) )
        {
            @Override
            protected void populateItem( ListItem item )
            {
                MenuItem panel = (MenuItem) item.getModelObject();

                if( item.getIndex() == 0 )
                {
                    panel.add( new AttributeModifier( "class", true, new Model( "first" ) ) );
                }

                item.add( panel );
            }
        };

        add( listView );
    }

    public abstract MenuItem[] getMenuItemList();

}

