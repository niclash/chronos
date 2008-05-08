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

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import java.util.Iterator;

public class SortableAccountDataProvider extends SortableDataProvider
{
    public SortableAccountDataProvider()
    {
        setSort( "name", true );
    }

    protected AccountDB getAccountDB()
    {
        return AccountDB.getSingletonInstance();
    }

    public Iterator iterator( int first, int count )
    {
        SortParam sp = getSort();
        return getAccountDB().find( first, count, sp.getProperty(), sp.isAscending() ).iterator();
    }

    public int size()
    {
        return getAccountDB().getCount();
    }

    public IModel model( Object object )
    {
        return new DetachableAccountModel( (Account) object );
    }
}
