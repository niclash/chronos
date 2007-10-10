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
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class SimpleTextArea extends TextArea
{
    private String text;

    public SimpleTextArea( String id, String text )
    {
        this( id, text, true );
    }

    public SimpleTextArea( String id, String text, boolean isReadOnly )
    {
        super( id );

        this.text = text;

        this.setModel( new PropertyModel( this, "text" ) );

        if( isReadOnly )
        {
            add( new AttributeModifier( "readonly", true, new Model( "readonly" ) ) );
        }
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;

        modelChanged();
    }
}
