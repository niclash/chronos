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
package org.qi4j.chronos.ui.staff;

import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.MoneyComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserAddPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffAddPage extends StaffAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( StaffAddPage.class );

    private LoginUserAddPanel loginUserAddPanel;

    public StaffAddPage( BasePage basePage, String accountId )
    {
        super( basePage, accountId );
    }

    public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
    {
        if( loginUserAddPanel == null )
        {
            loginUserAddPanel = new LoginUserAddPanel( id );
        }

        return loginUserAddPanel;
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Staff";
    }

    public void onSubmitting()
    {
        StaffService staffService = ChronosWebApp.getServices().getStaffService();

        StaffEntityComposite staff = staffService.newInstance( StaffEntityComposite.class );

        MoneyComposite money = ChronosWebApp.newInstance( MoneyComposite.class );

        staff.setSalary( money );

        LoginComposite login = ChronosWebApp.newInstance( LoginComposite.class );

        staff.setLogin( login );

        assignFieldValueToStaff( staff );

        try
        {
            AccountEntityComposite account = getAccount();

            account.addStaff( staff );

            AccountService accountService = ChronosWebApp.getServices().getAccountService();

            accountService.update( account );

            logInfoMsg( "Staff is added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage() );
        }
    }
}
