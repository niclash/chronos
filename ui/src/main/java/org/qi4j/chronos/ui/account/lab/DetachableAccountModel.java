/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.ui.account.lab;

import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.entity.Identity;

public class DetachableAccountModel extends LoadableDetachableModel
{
    private String id;

    protected AccountDB getAccountDB()
    {
        return AccountDB.getSingletonInstance();
    }

    public DetachableAccountModel( Account account )
    {
        this( ( (Identity) account).identity().get() );
    }

    public DetachableAccountModel( String id )
    {
        this.id = id;
    }

    public int hashCode()
    {
        return id.hashCode();
    }

    public boolean equals( final Object object )
    {
        if (object == this)
        {
            return true;
        }
        else if (object == null)
        {
            return false;
        }
        else if (object instanceof DetachableAccountModel)
        {
            DetachableAccountModel other = (DetachableAccountModel) object;
            return other.id == this.id;
        }
        return false;
    }

    protected Object load()
    {
        return getAccountDB().get( id );
    }
}
