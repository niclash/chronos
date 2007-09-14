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

import java.util.Iterator;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserEditPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StaffEditPage extends StaffAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( StaffEditPage.class );

    private LoginUserEditPanel loginUserEditPanel;

    public StaffEditPage( BasePage basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        Staff staff = getStaff();

        assignStaffToFieldValue( staff );
    }

    private StaffEntityComposite getStaff()
    {
        return getStaffService().get( getStaffId() );
    }

    public Iterator<SystemRoleEntityComposite> getInitSelectedRoleList()
    {
        return getStaff().systemRoleIterator();
    }

    public StaffService getStaffService()
    {
        return ChronosWebApp.getServices().getStaffService();
    }

    public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
    {
        if( loginUserEditPanel == null )
        {
            loginUserEditPanel = new LoginUserEditPanel( id, getStaffId() );
        }

        return loginUserEditPanel;
    }

    public String getSubmitButtonValue()
    {
        return "Edit";
    }

    public String getTitleLabel()
    {
        return "Edit staff";
    }

    public void onSubmitting()
    {
        StaffEntityComposite staff = getStaff();

        //TODO bp. system role is valueObject, let delete all assigned system role
        //TODO before assigning to new system roles. Seeking for correct solution.
        staff.removeAllSystemRole();

        assignFieldValueToStaff( staff );

        try
        {
            getStaffService().update( staff );

            logInfoMsg( "Staff is updated successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage() );
        }
    }

    public abstract String getStaffId();

}
