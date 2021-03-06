/*  Copyright 2008 Edward Yakop.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.domain.model.customer.assembly;

import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.api.query.Query;
import org.qi4j.api.service.ServiceFinder;
import org.qi4j.api.service.ServiceReference;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.customer.CustomerDetail;
import org.qi4j.chronos.domain.model.customer.CustomerFactory;
import org.qi4j.chronos.domain.model.user.contactPerson.ContactPerson;

public class CustomerTest extends AbstractCustomerTest
{
    @Test
    public void customerTest()
        throws UnitOfWorkCompletionException
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<CustomerFactory> factoryRef = serviceFinder.findService( CustomerFactory.class );
        CustomerFactory customerFactory = factoryRef.get();
        try
        {
            Customer customer = customerFactory.create( "Joe Smith", "Sir. Joe Smith" );
            assertNotNull( customer );
            assertNotNull( customer.customerId() );

            CustomerDetail customerDetail = customer.customerDetail();
            assertNotNull( customerDetail );
            assertEquals( "Joe Smith", customerDetail.name() );
            assertEquals( "Sir. Joe Smith", customerDetail.referenceName() );

            Query<ContactPerson> contactPersonQuery = customer.contactPersons();
            assertNotNull( contactPersonQuery );
            assertEquals( 0, contactPersonQuery.count() );

            Query<PriceRateSchedule> scheduleQuery = customer.priceRateSchedules();
            assertNotNull( scheduleQuery );
            assertEquals( 0, scheduleQuery.count() );
        }
        finally
        {
            uow.discard();
        }
    }
}
