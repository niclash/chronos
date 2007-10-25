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
import org.qi4j.api.annotation.scope.ThisAs;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.FindFilter;

public abstract class MockContactPersonMiscServiceMixin implements ContactPersonService
{
    @ThisAs private ContactPersonService contactPersonService;

    public List<ContactPersonEntityComposite> findAll( HasContactPersons hasContactPersons )
    {
        Iterator<ContactPersonEntityComposite> iter = hasContactPersons.contactPersonIterator();

        List<ContactPersonEntityComposite> resultList = new ArrayList<ContactPersonEntityComposite>();

        while( iter.hasNext() )
        {
            resultList.add( iter.next() );
        }

        return resultList;
    }

    public List<ContactPersonEntityComposite> findAll( HasContactPersons hasContactPersons, FindFilter findFilter )
    {
        return findAll( hasContactPersons ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public int countAll( HasContactPersons hasContactPersons )
    {
        return findAll( hasContactPersons ).size();
    }

    public void enableLogin( boolean enabled, Collection<ContactPersonEntityComposite> contactPersons )
    {
        for( ContactPersonEntityComposite contactPerson : contactPersons )
        {
            contactPerson.getLogin().setEnabled( enabled );

            contactPersonService.update( contactPerson );
        }
    }
}
