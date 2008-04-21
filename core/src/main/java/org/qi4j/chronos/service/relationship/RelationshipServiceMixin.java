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
package org.qi4j.chronos.service.relationship;

import org.qi4j.service.Activatable;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.ContactPerson;

import static org.qi4j.composite.NullArgumentException.validateNotNull;

public class RelationshipServiceMixin implements RelationshipService, Activatable
{
    public Relationship get( Customer customer, String relationshipName )
    {
        validateNotNull( "customer", customer );
        validateNotNull( "relationshipName", relationshipName );

        for( ContactPerson contactPerson : customer.contactPersons() )
        {
            if( relationshipName.equals( contactPerson.relationship().get().relationship().get() ) )
            {
                return contactPerson.relationship().get();
            }
        }

        return null;
    }

    public void activate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void passivate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
