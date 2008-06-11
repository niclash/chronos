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
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.injection.scope.Uses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountAddPage extends AccountAddEditPage
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger( AccountAddPage.class );

    public AccountAddPage( @Uses Page goBackPage, @Uses IModel<Account> accountModel )
    {
        super( goBackPage, accountModel );
    }

    public void onSubmitting( IModel<Account> accountModel )
    {
        try
        {
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

            logInfoMsg( "Account was added successfully" );

            super.divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( "Fail to create new account" );
            LOGGER.error( uowce.getMessage() );
        }
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
