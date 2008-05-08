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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.qi4j.composite.scope.Uses;
import static org.qi4j.composite.NullArgumentException.validateNotNull;


public class CustomerAddPage extends CustomerAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CustomerAddPage.class );
    private static final String ADD_SUCCESS = "addSuccessful";
    private static final String ADD_FAIL = "addFailed";
    private static final String SUBMIT_BUTTON = "addPageSubmitButton";
    private static final String TITLE_LABEL = "addPageTitleLabel";

    public CustomerAddPage( @Uses Page basePage )
    {
        super( basePage );

        validateNotNull( "basePage", basePage );

        bindModel();
    }

    private void bindModel()
    {
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        final UnitOfWork unitOfWork = getUnitOfWork();
                        final Customer customer = unitOfWork.newEntityBuilder( CustomerEntityComposite.class ).newInstance();
                        final Address address = unitOfWork.newEntityBuilder( AddressEntityComposite.class ).newInstance();
                        final City city = unitOfWork.newEntityBuilder( CityEntityComposite.class ).newInstance();
                        final State state = unitOfWork.newEntityBuilder( StateEntityComposite.class ).newInstance();
                        final Country country = unitOfWork.newEntityBuilder( CountryEntityComposite.class ).newInstance();

                        city.state().set( state );
                        city.country().set( country );
                        address.city().set( city );

                        customer.address().set( address );
                        customer.isEnabled().set( true );

                        return customer;
                    }
                }
            )
        );

        bindPropertyModel( getModel() );
    }

    public void onSubmitting()
    {
        try
        {
            final Customer customer = (Customer) getModelObject();
            final Account account = getAccount();
            account.customers().add( customer );

            getUnitOfWork().complete();
            logInfoMsg( getString( ADD_SUCCESS ) );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            error( getString( ADD_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );

            reset();
        }
        catch( Exception err)
        {
            error( getString( ADD_FAIL, new Model( err ) ) );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }
}
