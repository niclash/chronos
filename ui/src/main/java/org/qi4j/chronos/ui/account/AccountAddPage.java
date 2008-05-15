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
package org.qi4j.chronos.ui.account;

import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.library.framework.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AccountAddPage extends AccountAddEditPage
{
    private static final Logger LOGGER = LoggerFactory.getLogger( AccountAddPage.class );
    private static final String ADD_SUCCESS = "addSuccessful";
    private static final String ADD_FAIL = "addFailed";
    private static final String SUBMIT_BUTTON = "addPageSubmitButton";
    private static final String TITLE_LABEL = "addPageTitleLabel";

    public AccountAddPage( @Uses Page goBackPage )
    {
        super( goBackPage );

        validateNotNull( "goBackPage", goBackPage );

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
                        final Account account = unitOfWork.newEntityBuilder( AccountEntityComposite.class ).newInstance();
                        final Address address = unitOfWork.newEntityBuilder( AddressEntityComposite.class ).newInstance();
                        final City city = unitOfWork.newEntityBuilder( CityEntityComposite.class ).newInstance();
                        final State state = unitOfWork.newEntityBuilder( StateEntityComposite.class ).newInstance();
                        final Country country = unitOfWork.newEntityBuilder( CountryEntityComposite.class ).newInstance();

                        city.state().set( state );
                        city.country().set( country );
                        address.city().set( city );

                        account.address().set( address );
                        account.isEnabled().set( true );

                        return account;
                    }
                }
            )
        );

        bindPropertyModel( getModel() );
    }

    public void onSubmitting()
    {
        final UnitOfWork unitOfWork = getUnitOfWork();
        try
        {
            final Account account = (Account) getModelObject();
            getAccountService().add( account );
            unitOfWork.complete();
            logInfoMsg( getString( ADD_SUCCESS ) );

            super.divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            unitOfWork.reset();

            logErrorMsg( getString( ADD_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage() );
        }
        catch( ValidationException validationErr )
        {
            unitOfWork.reset();

            logErrorMsg( getString( ADD_FAIL, new Model( validationErr ) ) );
            LOGGER.error( validationErr.getMessage(), validationErr );
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
