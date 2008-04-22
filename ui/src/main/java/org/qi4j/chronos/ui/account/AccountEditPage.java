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
import org.apache.wicket.Session;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.composite.scope.Uses;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Service;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountEditPage extends AccountAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( AccountEditPage.class );

    transient private @Structure UnitOfWorkFactory factory;

    private String accountId;

    public AccountEditPage( @Uses Page goBackPage, @Uses String accountId )
    {
        super( goBackPage );

        this.accountId = accountId;

        assignAccountToFieldValue( getAccount() );
    }

    protected Account getAccount()
    {
        return getAccountService().get( accountId );
    }

    protected AccountService getAccountService()
    {
        return ( ( ChronosSession ) Session.get()).getAccountService();
    }

    public void onSubmitting()
    {
        if( !nameField.getText().equals( getAccount().name().get() ) &&
            !getAccountService().isUnique( nameField.getText() ) )
        {
            logErrorMsg( "Account name " + nameField.getText() + " is not unique!!!" );

            return;
        }

        final UnitOfWork unitOfWork;

        if( null != factory.currentUnitOfWork() && factory.currentUnitOfWork().isOpen() )
        {
            unitOfWork = factory.currentUnitOfWork();
            System.err.println( "Using existing unit of work" );
        }
        else
        {
            unitOfWork = factory.newUnitOfWork();
            System.err.println( "Got new unit of work" );
        }

        try
        {
            Account account = getAccount();

            assignFieldValueToAccount( account );

            getAccountService().add( account );

            unitOfWork.complete();
            logInfoMsg( "Account is updated successfully." );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            logErrorMsg( "Unable to update account!!!. " + uowce.getClass().getSimpleName() );
            LOGGER.error( uowce.getLocalizedMessage() );

            if( null != unitOfWork && unitOfWork.isOpen() )
            {
                unitOfWork.discard();
            }
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Account";
    }
}
