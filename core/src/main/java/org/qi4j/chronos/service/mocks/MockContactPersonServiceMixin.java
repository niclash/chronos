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

import java.util.Iterator;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.entity.association.SetAssociation;

public abstract class MockContactPersonServiceMixin extends MockCustomerBasedServiceMixin<ContactPersonEntityComposite>
{
    public MockContactPersonServiceMixin()
    {
        super();
    }

    /* protected Iterator<ContactPersonEntityComposite> getItems( CustomerEntityComposite customerEntityComposite )
    {
        return customerEntityComposite.contactPersons().iterator();
    }

    protected void removeItem( CustomerEntityComposite customer, ContactPersonEntityComposite contactPerson )
    {
        SetAssociation<ContactPersonEntityComposite> customerContacts = customer.contactPersons();
        customerContacts.remove( contactPerson );
    } */
}
