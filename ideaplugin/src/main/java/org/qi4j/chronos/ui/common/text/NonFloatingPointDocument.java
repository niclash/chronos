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
package org.qi4j.chronos.ui.common.text;

import javax.swing.text.BadLocationException;

public class NonFloatingPointDocument extends ValidatableDocument
{
    private boolean isAcceptNegative;

    public NonFloatingPointDocument()
    {
        this( true );
    }

    public NonFloatingPointDocument( boolean acceptNegativeValue )
    {
        isAcceptNegative = acceptNegativeValue;
    }

    public boolean isAcceptNegativeValue()
    {
        return isAcceptNegative;
    }

    protected boolean isAcceptThisChar( char aChar, int currentIndex ) throws BadLocationException
    {
        if( !Character.isDigit( aChar ) && aChar != '-' )
        {
            return false;
        }

        if( aChar == '-' )
        {
            if( !isAcceptNegative )
            {
                return false;
            }

            //only valid if it is first character
            if( currentIndex != 0 || getLength() != 0 )
            {
                return false;
            }
        }

        return true;
    }

}