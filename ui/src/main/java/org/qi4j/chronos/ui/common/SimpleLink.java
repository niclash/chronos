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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class SimpleLink extends Panel
{
    private Link link;
    private Label label;

    public SimpleLink( String id, String text )
    {
        super( id );

        link = new Link( "link" )
        {
            @Override
            public void onClick()
            {
                linkClicked();
            }
        };

        label = new Label( "linkTextLabel", text );
        label.setEscapeModelStrings( false );
        link.add( label );

        add( link );
    }

    public void setText( String text )
    {
        label.setModelObject( text );
    }

    public void addAttributeModifierToLink( AttributeModifier attributeModifier )
    {
        link.add( attributeModifier );
    }

    public void setPopupSettings( PopupSettings popupSettings )
    {
        link.setPopupSettings( popupSettings );
    }

    public final void setLinkEnabled( boolean enabled )
    {
        link.setEnabled( enabled );
    }

    public abstract void linkClicked();
}

