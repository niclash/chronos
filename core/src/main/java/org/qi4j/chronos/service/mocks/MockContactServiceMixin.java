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
import java.util.List;
import org.qi4j.chronos.model.associations.HasContacts;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.service.ContactService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.scope.Structure;
import org.qi4j.entity.association.SetAssociation;
import org.qi4j.library.general.model.Contact;

public class MockContactServiceMixin implements ContactService
{
    @Structure private CompositeBuilderFactory factory;

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
        SetAssociation<ContactComposite> contacts = hasContacts.contacts();

        for( ContactComposite contact : contacts )
        {
            boolean next = loopCallBack.callBack( contact );

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
                if( contact.contactValue().get().equals( contactValue ) )
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
        SetAssociation<ContactComposite> contacts = hasContacts.contacts();

        ContactComposite toBeDeleted = null;

        for( ContactComposite contact : contacts )
        {
            if( contact.contactType().get().equals( oldContact.contactType().get() ) &&
                contact.contactValue().get().equals( oldContact.contactValue().get() ) )
            {
                toBeDeleted = contact;
                break;
            }
        }

        contacts.remove( toBeDeleted );
        contacts.add( newContact );
    }

    public void deleteContact( HasContacts hasContacts, Collection<ContactComposite> contacts )
    {
        SetAssociation<ContactComposite> target = hasContacts.contacts();
        for( ContactComposite contact : contacts )
        {
            ContactComposite toBeDeleted = null;

            for( ContactComposite temp : target )
            {

                if( temp.contactValue().get().equals( contact.contactValue().get() ) &&
                    temp.contactType().get().equals( contact.contactType().get() ) )
                {
                    toBeDeleted = temp;
                    break;
                }
            }

            target.remove( toBeDeleted );
        }
    }
}
