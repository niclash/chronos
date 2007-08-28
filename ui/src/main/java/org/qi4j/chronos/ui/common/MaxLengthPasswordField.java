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
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.qi4j.chronos.ui.util.ValidatorUtil;

public class MaxLengthPasswordField extends PasswordTextField
{
    private String text;
    private String fieldName;
    private int maxLength;

    public MaxLengthPasswordField( String id, String fieldName, int maxLength )
    {
        super( id );

        if( fieldName == null )
        {
            throw new IllegalArgumentException( "[FieldName] must not be null!" );
        }

        if( maxLength < 1 )
        {
            throw new IllegalArgumentException( "[MaxLength] must not be equal or less than 0." );
        }

        this.fieldName = fieldName;
        this.maxLength = maxLength;

        this.add( new AttributeModifier( "maxlength", true, new Model( String.valueOf( maxLength ) ) ) );
        this.setModel( new PropertyModel( this, "text" ) );
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public boolean checkIsNullOrInvalidLength()
    {
        return ValidatorUtil.isEmptyOrInvalidLength( text, fieldName, maxLength, this );
    }
}

