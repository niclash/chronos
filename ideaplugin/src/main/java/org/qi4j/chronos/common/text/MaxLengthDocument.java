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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MaxLengthDocument extends PlainDocument
{
    private int maxLength = Integer.MAX_VALUE;

    public MaxLengthDocument( int maxLength )
    {
        if( maxLength <= 0 )
        {
            throw new IllegalArgumentException( "MaxLength must be greater than zero." );
        }

        this.maxLength = maxLength;
    }

    public int getMaxLength()
    {
        return maxLength;
    }

    public void insertString( int offs, String str, AttributeSet a ) throws BadLocationException
    {
        if( str == null )
        {
            return;
        }

        if( ( getLength() + str.length() ) <= maxLength )
        {
            super.insertString( offs, str, a );
        }
    }
}

