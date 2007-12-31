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
package org.qi4j.chronos.common.text;

import com.intellij.openapi.util.text.StringUtil;

public class JNonFloatingPointTextField extends AbstractTextField
{
    public JNonFloatingPointTextField()
    {
        this( true );
    }

    public JNonFloatingPointTextField( boolean acceptNegativeValue )
    {
        super( new NonFloatingPointDocument( acceptNegativeValue ), "", 0 );
    }

    public void setIntValue( int value )
    {
        setText( String.valueOf( value ) );
    }

    public void setShortValue( short value )
    {
        setText( String.valueOf( value ) );
    }

    public void setLongValue( long value )
    {
        setText( String.valueOf( value ) );
    }

    public int getIntValue()
    {
        if( !StringUtil.isEmptyOrSpaces( getText() ) )
        {
            return Integer.parseInt( getText() );
        }

        return 0;
    }

    public short getShortValue()
    {
        if( !StringUtil.isEmptyOrSpaces( getText() ) )
        {
            return Short.parseShort( getText() );
        }

        return 0;
    }

    public long getLongValue()
    {
        if( !StringUtil.isEmptyOrSpaces( getText() ) )
        {
            return Long.parseLong( getText() );
        }

        return 0L;
    }
}
