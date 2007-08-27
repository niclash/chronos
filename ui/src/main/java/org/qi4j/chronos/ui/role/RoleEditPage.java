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
package org.qi4j.chronos.ui.role;

import org.qi4j.chronos.model.composites.RoleEntityComposite;
import org.qi4j.chronos.service.RoleService;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleEditPage extends RoleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( RoleEditPage.class );

    private String roleId;

    public RoleEditPage( BasePage goBackPage, String roleId )
    {
        super( goBackPage );

        this.roleId = roleId;

        nameField.setText( getRole().getRole() );
    }

    private RoleEntityComposite getRole()
    {
        return getRoleService().get( roleId );
    }

    public String getSubmitButtonValue()
    {
        return "Edit";
    }

    public String getTitleLabel()
    {
        return "Edit Role";
    }

    public void onSubmitting()
    {
        RoleService roleService = getRoleService();

        RoleEntityComposite role = getRole();

        role.setRole( nameField.getText() );

        try
        {
            roleService.update( role );

            logMsg( "Role is updated successfull!" );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            LOGGER.error( err.getMessage(), err );
        }
    }
}
