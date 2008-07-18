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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class MenuItemPanel extends Panel
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_MENU_ITEM = "menuItem";

    private static final String WICKET_ID_LINK_LABEL = "linkLabel";
    private static final String WICKET_ID_LINK = "link";

    public MenuItemPanel( IModel aLabel )
    {
        super( WICKET_ID_MENU_ITEM );

        Link link = new MenuItemLink( this );
        add( link );

        Label label = new Label( WICKET_ID_LINK_LABEL, aLabel );
        link.add( label );
    }

    protected abstract void handleClicked();

    private static class MenuItemLink extends Link
    {
        private static final long serialVersionUID = 1L;
        private final MenuItemPanel menuItem;

        public MenuItemLink( MenuItemPanel aMenuItem )
        {
            super( WICKET_ID_LINK );
            menuItem = aMenuItem;
        }

        @Override
        public void onClick()
        {
            menuItem.handleClicked();
        }
    }
}

