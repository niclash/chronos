/*
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
import org.qi4j.chronos.model.associations.HasContacts;
import org.qi4j.chronos.model.composites.ContactComposite;

/**
 * Default mixin implementation for {@link org.qi4j.chronos.model.associations.HasContacts}
 */
public final class HasContactsMixin implements HasContacts
{
    private final List<ContactComposite> contacts;

    public HasContactsMixin()
    {
        contacts = new ArrayList<ContactComposite>();
    }

    public void addContact( ContactComposite contact )
    {
        contacts.add( contact );
    }

    public void removeContact( ContactComposite contact )
    {
        contacts.remove( contact );
    }

    public Iterator<ContactComposite> contactIterator()
    {
        return contacts.iterator();
    }

    public void removeAllContact()
    {
        contacts.clear();
    }
}
