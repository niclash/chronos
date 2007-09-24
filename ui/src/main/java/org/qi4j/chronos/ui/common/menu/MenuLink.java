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

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public class MenuLink extends MenuItem
{
    private static final long serialVersionUID = 1L;

    private Class<? extends WebPage> webPageClazz;
    private PageParameters pageParamaters;
    private String text;

    public MenuLink( String text, final Class<? extends WebPage> webPage )
    {
        this( text, webPage, null );
    }

    public MenuLink( String text, final Class<? extends WebPage> webPage, final PageParameters pageParameters )
    {
        super();

        this.webPageClazz = webPage;
        this.pageParamaters = pageParameters;
        this.text = text;

        initComponents();
    }

    private void initComponents()
    {
        Link link = new Link( "link", new Model() )
        {
            @Override
            public void onClick()
            {
                if( pageParamaters != null )
                {
                    setResponsePage( webPageClazz, pageParamaters );
                }
                else
                {
                    setResponsePage( webPageClazz );
                }
            }
        };

        Label label = new Label( "linkLabel", text );
        link.add( label );

        add( link );
    }
}

