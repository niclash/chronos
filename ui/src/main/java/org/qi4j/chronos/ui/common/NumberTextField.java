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
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.qi4j.chronos.ui.util.ValidatorUtil;

public class NumberTextField extends TextField
{
    private String text;
    private String fieldName;

    public NumberTextField( String id, String fieldName )
    {
        super( id );

        this.fieldName = fieldName;

        this.add( new AttributeModifier( "maxlength", true, new Model( String.valueOf( 10 ) ) ) );

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

    public int getIntValue()
    {
        return Integer.parseInt( this.getDefaultModelObjectAsString() );
    }

    public long getLongValue()
    {
        return Long.parseLong( this.getDefaultModelObjectAsString() );
    }

    public double getDoubleValue()
    {
        return Double.parseDouble( this.getDefaultModelObjectAsString() );
    }

    public void setIntValue( int intValue )
    {
        text = String.valueOf( intValue );
    }

    public void setLongValue( long longValue )
    {
        text = String.valueOf( longValue );
    }

    public void setDoubleValue( double doubleValue )
    {
        text = String.valueOf( doubleValue );
    }

    public boolean checkIsEmptyOrNotLong()
    {
        if( !ValidatorUtil.isEmpty( this.getDefaultModelObjectAsString(), fieldName, this ) )
        {
            return ValidatorUtil.isNotLong( this.getDefaultModelObjectAsString(), fieldName, this );
        }

        return true;
    }

    public boolean checkIsEmptyOrNotInteger()
    {
        if( !ValidatorUtil.isEmpty( this.getDefaultModelObjectAsString(), fieldName, this ) )
        {
            return ValidatorUtil.isNotInteger( this.getDefaultModelObjectAsString(), fieldName, this );
        }

        return true;
    }

    public boolean checkIsEmptyOrNotDouble()
    {
        if( !ValidatorUtil.isEmpty( this.getDefaultModelObjectAsString(), fieldName, this ) )
        {
            return ValidatorUtil.isNotDouble( this.getDefaultModelObjectAsString(), fieldName, this );
        }

        return true;
    }

}

