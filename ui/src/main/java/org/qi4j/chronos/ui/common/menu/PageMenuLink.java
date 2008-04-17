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

public class PageMenuLink extends MenuLink
{
    private Class<? extends WebPage> webPageClazz;
    private PageParameters pageParamaters;

    public PageMenuLink( String text, final Class<? extends WebPage> webPage )
    {
        this( text, webPage, null );
    }

    public PageMenuLink( String text, final Class<? extends WebPage> webPage, final PageParameters pageParameters )
    {
        super( text );

        this.webPageClazz = webPage;
        this.pageParamaters = pageParameters;
    }

    protected void handleClicked()
    {
        if( pageParamaters != null )
        {
            setResponsePage( getPageFactory().newPage( webPageClazz, pageParamaters ) );
        }
        else
        {
            setResponsePage( newPage( webPageClazz ) );
        }
    }
}
