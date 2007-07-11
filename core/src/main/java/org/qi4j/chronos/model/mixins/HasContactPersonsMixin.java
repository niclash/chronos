/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.associations.HasContactPersons;

public final class HasContactPersonsMixin implements HasContactPersons
{
    private final List<ContactPerson> list;

    public HasContactPersonsMixin()
    {
        list = new ArrayList<ContactPerson>();
    }

    public Iterator<ContactPerson> contactPersonIterator()
    {
        return list.iterator();
    }

    public void addContactPerson( ContactPerson contactPerson )
    {
        list.add( contactPerson );
    }

    public void removeContactPerson( ContactPerson contactPerson )
    {
        list.remove( contactPerson );
    }
}
