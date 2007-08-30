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

import org.qi4j.chronos.model.composites.AddressComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.library.general.model.composites.CityEntityComposite;
import org.qi4j.library.general.model.composites.CountryEntityComposite;
import org.qi4j.library.general.model.composites.StateEntityComposite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerAddPage extends CustomerAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CustomerAddPage.class );

    public CustomerAddPage( BasePage basePage )
    {
        super( basePage );
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Customer";
    }

    public void onSubmitting()
    {
        CustomerService customerService = ChronosWebApp.getServices().getCustomerService();
        CustomerEntityComposite customer = customerService.newInstance( CustomerEntityComposite.class );

        customer.setName( fullNameField.getText() );
        customer.setReference( referenceField.getText() );

        AddressComposite address = ChronosWebApp.newInstance( AddressComposite.class );
        address.setFirstLine( addressAddEditPanel.getAddress1Field().getText() );
        address.setSecondLine( addressAddEditPanel.getAddress2Field().getText() );

        address.setZipCode( addressAddEditPanel.getZipcodeField().getText() );

        CityEntityComposite city = ChronosWebApp.newInstance( CityEntityComposite.class );

        city.setName( addressAddEditPanel.getCityField().getText() );

        StateEntityComposite state = ChronosWebApp.newInstance( StateEntityComposite.class );

        state.setName( addressAddEditPanel.getStateField().getText() );

        city.setState( state );

        CountryEntityComposite country = ChronosWebApp.newInstance( CountryEntityComposite.class );
        country.setName( addressAddEditPanel.getCountryField().getText() );

        city.setCountry( country );

        address.setCity( city );

        try
        {
            customerService.save( customer );

            logInfoMsg( "Customer is added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }
}
