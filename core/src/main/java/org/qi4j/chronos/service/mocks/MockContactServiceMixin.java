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
package org.qi4j.chronos.service.mocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.qi4j.CompositeBuilderFactory;
import org.qi4j.annotation.scope.Qi4j;
import org.qi4j.chronos.model.associations.HasContacts;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.service.ContactService;
import org.qi4j.chronos.service.FindFilter;

public class MockContactServiceMixin implements ContactService
{
    @Qi4j private CompositeBuilderFactory factory;

    public List<ContactComposite> findAll( HasContacts hasContacts, FindFilter findFilter )
    {
        return findAll( hasContacts ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<ContactComposite> findAll( HasContacts hasContacts )
    {
        final List<ContactComposite> list = new ArrayList<ContactComposite>();

        loopContacts( hasContacts, new LoopCallBack<ContactComposite>()
        {
            public boolean callBack( ContactComposite contact )
            {
                list.add( CloneUtil.cloneContact( factory, contact ) );

                return true;
            }
        } );

        return list;
    }

    private void loopContacts( HasContacts hasContacts, LoopCallBack<ContactComposite> loopCallBack )
    {
        Iterator<ContactComposite> iter = hasContacts.contactIterator();

        while( iter.hasNext() )
        {
            boolean next = loopCallBack.callBack( iter.next() );

            if( !next )
            {
                break;
            }
        }
    }

    public int countAll( HasContacts hasContacts )
    {
        return findAll( hasContacts ).size();
    }

    public ContactComposite get( HasContacts hasContacts, final String contactValue )
    {
        final ContactComposite[] returnValue = new ContactComposite[1];

        loopContacts( hasContacts, new LoopCallBack<ContactComposite>()
        {
            public boolean callBack( ContactComposite contact )
            {
                if( contact.getContactValue().equals( contactValue ) )
                {
                    returnValue[ 0 ] = CloneUtil.cloneContact( factory, contact );
                    return false;
                }

                return true;
            }
        } );

        return returnValue[ 0 ];
    }

    public void update( HasContacts hasContacts, ContactComposite oldContact, ContactComposite newContact )
    {
        Iterator<ContactComposite> contactIter = hasContacts.contactIterator();

        ContactComposite toBeDeleted = null;

        while( contactIter.hasNext() )
        {
            ContactComposite temp = contactIter.next();

            if( temp.getContactType().equals( oldContact.getContactType() ) && temp.getContactValue().equals( oldContact.getContactValue() ) )
            {
                toBeDeleted = temp;
            }
        }

        hasContacts.removeContact( toBeDeleted );
        hasContacts.addContact( newContact );
    }

    public void deleteContact( HasContacts hasContacts, Collection<ContactComposite> contacts )
    {
        for( ContactComposite contact : contacts )
        {
            ContactComposite toBeDeleted = null;

            Iterator<ContactComposite> contactIter = hasContacts.contactIterator();

            while( contactIter.hasNext() )
            {
                ContactComposite tempContact = contactIter.next();

                if( tempContact.getContactValue().equals( contact.getContactValue() ) &&
                    tempContact.getContactType().equals( contact.getContactType() ) )
                {
                    toBeDeleted = tempContact;
                    break;
                }
            }

            hasContacts.removeContact( toBeDeleted );
        }
    }
}
