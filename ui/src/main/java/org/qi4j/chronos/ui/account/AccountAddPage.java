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
import org.qi4j.library.framework.validation.ValidationException;
import org.qi4j.composite.scope.Uses;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Service;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;

import static org.qi4j.composite.NullArgumentException.validateNotNull;

public class AccountAddPage extends AccountAddEditPage
{
    transient private UnitOfWorkFactory factory;

    public AccountAddPage( @Uses Page goBackPage, final @Structure UnitOfWorkFactory factory )
    {
        super( goBackPage );

        validateNotNull( "goBackPage", goBackPage );
        validateNotNull( "factory", factory );

        this.factory = factory;
    }

    public void onSubmitting()
    {
        if( !getAccountService().isUnique( nameField.getText() ) )
        {
            logErrorMsg( "Account name " + nameField.getText() + " is not unique!!!" );

            return;
        }

        final UnitOfWork unitOfWork = factory.newUnitOfWork();
        try
        {

            Account account = unitOfWork.newEntityBuilder( AccountEntityComposite.class ).newInstance();
            Address address = unitOfWork.newEntityBuilder( AddressEntityComposite.class ).newInstance();
            City city = unitOfWork.newEntityBuilder( CityEntityComposite.class ).newInstance();
            State state = unitOfWork.newEntityBuilder( StateEntityComposite.class ).newInstance();
            Country country = unitOfWork.newEntityBuilder( CountryEntityComposite.class ).newInstance();

            city.state().set( state );
            city.country().set( country );
            address.city().set( city );

            account.address().set( address );
            account.isEnabled().set( true );

            assignFieldValueToAccount( account );

            getAccountService().add( account );

            unitOfWork.complete();

            logInfoMsg( "Account is added successfully." );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            logErrorMsg( "Unable to save account!!!. " + uowce.getClass().getSimpleName() );

            if( null != unitOfWork && unitOfWork.isOpen() )
            {
                unitOfWork.discard();
            }
        }
        catch( ValidationException validationErr )
        {
            logErrorMsg( validationErr.getMessages() );
        }
    }

    protected AccountService getAccountService()
    {
        return ChronosSession.get().getAccountService();
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "New Account";
    }
}
