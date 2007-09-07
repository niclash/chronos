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

import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountEditPage extends AccountAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( AccountEditPage.class );

    private String accountId;

    public AccountEditPage( BasePage goBackPage, String accountId )
    {
        super( goBackPage );

        this.accountId = accountId;

        Account account = ChronosWebApp.getServices().getAccountService().get( accountId );

        customerAddEditPanel.assignCustomerToFields( account );
    }

    public void onSubmitting()
    {
        AccountService accountService = ChronosWebApp.getServices().getAccountService();
        AccountEntityComposite account = accountService.get( accountId );

        try
        {
            customerAddEditPanel.assignFieldValueToCustomer( account );

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
        return "Update";
    }

    public String getTitleLabel()
    {
        return "Edit Account";
    }
}
