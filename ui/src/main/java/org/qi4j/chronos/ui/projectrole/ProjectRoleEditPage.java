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
package org.qi4j.chronos.ui.projectrole;

import org.apache.wicket.Page;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.service.ProjectRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectRoleEditPage extends ProjectRoleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectRoleEditPage.class );

    private String roleId;

    public ProjectRoleEditPage( Page goBackPage, String roleId )
    {
        super( goBackPage );

        this.roleId = roleId;

        initData();
    }

    private void initData()
    {
        nameField.setText( getRole().getProjectRole() );
    }

    private ProjectRoleEntityComposite getRole()
    {
        return getRoleService().get( roleId );
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit ProjectRole";
    }

    public void onSubmitting()
    {
        ProjectRoleService roleService = getRoleService();

        ProjectRoleEntityComposite projectRole = getRole();

        projectRole.setProjectRole( nameField.getText() );

        try
        {
            roleService.update( projectRole );

            logInfoMsg( "ProjectRole is updated successfull!" );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            LOGGER.error( err.getMessage(), err );
        }
    }
}
