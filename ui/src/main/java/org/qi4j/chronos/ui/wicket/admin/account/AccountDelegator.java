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
package org.qi4j.chronos.ui.wicket.admin.account;

import java.io.Serializable;
import org.qi4j.chronos.model.Account;
import org.qi4j.entity.Identity;

public class AccountDelegator implements Serializable
{
    private String name;
    private String id;

    public AccountDelegator( Account account )
    {
        this.name = account.name().get();
        this.id = ( (Identity) account).identity().get();
    }

    public AccountDelegator( String name, String id )
    {
        this.name = name;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public boolean equals( Object o )
    {
        if( this == o )
        {
            return true;
        }
        if( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        AccountDelegator that = (AccountDelegator) o;

        if( id != null ? !id.equals( that.id ) : that.id != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return ( id != null ? id.hashCode() : 0 );
    }

    public String toString()
    {
        return name;
    }
}
