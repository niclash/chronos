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

import org.apache.wicket.Page;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserAddPanel;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.MoneyEntityComposite;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.Money;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.Account;
import org.qi4j.library.framework.validation.ValidationException;

public class StaffAddPage extends StaffAddEditPage
{
    private LoginUserAddPanel loginUserAddPanel;

    public StaffAddPage( Page basePage )
    {
        super( basePage );
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
        StaffService staffService = getServices().getStaffService();

        Staff staff = staffService.newInstance( StaffEntityComposite.class );

        Money money = ChronosWebApp.newInstance( MoneyEntityComposite.class );

        staff.salary().set( money );

        Login login = ChronosWebApp.newInstance( LoginEntityComposite.class );

        staff.login().set( login );

        assignFieldValueToStaff( staff );

        try
        {
            Account account = getAccount();

            account.staffs().add( staff );

            AccountService accountService = getServices().getAccountService();

            // TODO migrate
//            accountService.update( account );

            logInfoMsg( "Staff is added successfully." );

            divertToGoBackPage();
        }
        catch( ValidationException err )
        {
            logErrorMsg( err.getMessages() );
        }
    }
}
