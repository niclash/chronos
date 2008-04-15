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
package org.qi4j.chronos.ui.customer;

import org.apache.wicket.Page;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerAddPage extends CustomerAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CustomerAddPage.class );

    public CustomerAddPage( Page basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
        CustomerService service = ChronosWebApp.getServices().getCustomerService();

        Customer customer = service.newInstance( CustomerEntityComposite.class );

        try
        {
            Address address = ChronosWebApp.newInstance( AddressEntityComposite.class );
            City city = ChronosWebApp.newInstance( CityEntityComposite.class );
            State state = ChronosWebApp.newInstance( StateEntityComposite.class );
            Country country = ChronosWebApp.newInstance( CountryEntityComposite.class );

            address.city().set( city );

            city.state().set( state );
            city.country().set( country );

            customer.address().set( address );

            assignFieldValueToCustomer( customer );

            AccountService accountService = ChronosWebApp.getServices().getAccountService();

            Account account = getAccount();

            account.customers().add( customer );

            // TODO migrate
//            accountService.update( account );

            logInfoMsg( "Customer is added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Customer";
    }
}
