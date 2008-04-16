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
package org.qi4j.chronos.model;

import java.lang.System;

public enum SystemRoleTypeEnum
{
    ADMIN( "Admin" ), STAFF( "Staff" ), CONTACT_PERSON( "Contact Person" );

    private String description;

    private SystemRoleTypeEnum( String description )
    {
        this.description = description;
    }

    @Override public String toString()
    {
        return this.description;
    }

    public static final SystemRoleTypeEnum toEnum( String text )
    {
        try
        {
            return valueOf( text.toUpperCase() );
        }
        catch( IllegalArgumentException iae )
        {
            System.err.println( iae.getLocalizedMessage() );
            iae.printStackTrace();
        }
        
        return null;
    }
}
