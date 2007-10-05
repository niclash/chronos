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
package org.qi4j.chronos.ui.common;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class NewLinkPanel extends BorderPanel
{
    public NewLinkPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        add( getContent( "content" ) );

        Label linkLabel = new Label( "linkLabel", getNewLinkText() );

        Link link = new Link( "link" )
        {
            public void onClick()
            {
                newLinkOnClick();
            }
        };

        link.add( linkLabel );

        add( link );
    }

    public abstract Panel getContent( String id );

    public abstract void newLinkOnClick();

    public final Link getNewLink( String id )
    {
        return null;
    }

    public abstract String getNewLinkText();
}
