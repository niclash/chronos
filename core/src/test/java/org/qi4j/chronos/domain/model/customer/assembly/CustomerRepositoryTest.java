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
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.customer.CustomerDetail;
import org.qi4j.chronos.domain.model.customer.CustomerFactory;
import org.qi4j.chronos.domain.model.customer.CustomerId;
import org.qi4j.chronos.domain.model.customer.CustomerRepository;

public class CustomerRepositoryTest extends AbstractCustomerTest
{
    @Test
    public void findTest()
        throws UnitOfWorkCompletionException
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<CustomerFactory> factoryRef = serviceFinder.findService( CustomerFactory.class );
        ServiceReference<CustomerRepository> repositoryRef = serviceFinder.findService( CustomerRepository.class );

        CustomerFactory customerFactory = factoryRef.get();
        CustomerRepository customerRepository = repositoryRef.get();

        try
        {
            Customer joeCustomer = customerFactory.create( "Joe Smith", "Sir Joe Smith" );
            CustomerId joeCustomerId = joeCustomer.customerId();
            Customer johnCustomer = customerFactory.create( "John Smith", "Captain John Smith" );
            uow.apply();

            // Find individual
            Customer customer1 = customerRepository.find( joeCustomerId );
            assertTrue( joeCustomerId.sameValueAs( customer1.customerId() ) );
            CustomerDetail customerDetail = customer1.customerDetail();
            assertEquals( "Joe Smith", customerDetail.name() );
            assertEquals( "Sir Joe Smith", customerDetail.referenceName() );

            // Find all
            Query<Customer> query = customerRepository.findAll();
            assertEquals( 2, query.count() );

            uow.remove( joeCustomer );
            uow.remove( johnCustomer );
            uow.apply();
        }
        finally
        {
            uow.discard();
        }
    }
}
