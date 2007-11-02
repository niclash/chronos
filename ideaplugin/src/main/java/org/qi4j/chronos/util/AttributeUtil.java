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
package org.qi4j.chronos.util;

import org.jdom.Attribute;
import org.jdom.Element;

public final class AttributeUtil
{
    private final static String VALUE = "value";

    public static void setAttribute( Element element, String elementName, int value )
    {
        setAttribute( element, elementName, String.valueOf( value ) );
    }

    public static void setAttribute( Element element, String elementName, boolean value )
    {
        setAttribute( element, elementName, String.valueOf( value ) );
    }

    public static void setAttribute( Element element, String elementName, String value )
    {
        if( value != null )
        {
            Element newElement = new Element( elementName );
            newElement = newElement.setAttribute( VALUE, value );
            element.addContent( newElement );
        }
    }

    public static String getStringAttribute( Element element, String elementName )
    {
        Element aElement = element.getChild( elementName );

        if( aElement != null )
        {
            Attribute attribute = aElement.getAttribute( VALUE );

            if( attribute != null )
            {
                return attribute.getValue();
            }
        }

        return null;
    }

    public static int getIntAttribute( Element element, String elementName, int defaultValue )
    {
        String value = getStringAttribute( element, elementName );

        if( value != null )
        {
            return Integer.parseInt( value );
        }

        return defaultValue;
    }

    public static boolean getBooleanAttribute( Element element, String elementName, boolean defaultValue )
    {
        String value = getStringAttribute( element, elementName );

        if( value != null )
        {
            return Boolean.parseBoolean( value );
        }

        return defaultValue;
    }
}
