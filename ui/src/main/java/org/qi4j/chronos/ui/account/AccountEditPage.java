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
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountEditPage extends AccountAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( AccountEditPage.class );

    private String accountId;

    public AccountEditPage( Page goBackPage, String accountId )
    {
        super( goBackPage );

        this.accountId = accountId;

        assignAccountToFieldValue( getAccount() );
    }

    protected AccountEntityComposite getAccount()
    {
        return ChronosWebApp.getServices().getAccountService().get( accountId );
    }

    public void onSubmitting()
    {
        AccountService accountService = ChronosWebApp.getServices().getAccountService();
        AccountEntityComposite account = getAccount();

        account.setEnabled( true );

        try
        {
            assignFieldValueToAccount( account );

            accountService.update( account );

            logInfoMsg( "Account is updated successfully." );

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
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Account";
    }
}
