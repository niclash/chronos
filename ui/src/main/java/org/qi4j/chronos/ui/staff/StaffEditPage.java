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
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StaffEditPage extends StaffAddEditPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( StaffEditPage.class );

    public StaffEditPage( Page basePage, IModel<Staff> staffModel )
    {
        super( basePage, staffModel );
    }

    @Override
    public Iterator<SystemRole> getInitSelectedRoleList()
    {
        return getStaff().systemRoles().iterator();
    }

/*
    public AbstractUserLoginPanel getLoginUserAbstractPanel( String id, IModel<Login> loginModel )
    {
        if( userLoginEditPanel == null )
        {
            userLoginEditPanel = new UserLoginEditPanel( id, loginModel );
        }

        return userLoginEditPanel;
    }
*/

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Staff";
    }

    public void onSubmitting()
    {
        try
        {
            final Staff staff = (Staff) getDefaultModelObject();
            staff.systemRoles().clear();
            for( SystemRole systemRole : getUserAddEditPanel().getSelectedRoleList() )
            {
                staff.systemRoles().add( systemRole );
            }
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            logInfoMsg( "Staff was saved successfully." );

            divertToGoBackPage();
        }
        catch( Exception uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( "Fail to save staff." );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    public abstract Staff getStaff();
}
