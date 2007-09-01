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

import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.service.ProjectRoleService;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectRoleAddPage extends ProjectRoleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectRoleAddPage.class );

    public ProjectRoleAddPage( BasePage goBackpage )
    {
        super( goBackpage );
    }

    public void onSubmitting()
    {
        ProjectRoleService roleService = getRoleService();

        ProjectRoleEntityComposite projectRole = roleService.newInstance( ProjectRoleEntityComposite.class );

        projectRole.setRole( nameField.getText() );

        try
        {
            roleService.save( projectRole );

            logInfoMsg( "ProjectRole is added successfully!" );

            BasePage goBackPage = getGoBackPage();
            setResponsePage( new ProjectRoleDetailPage( goBackPage, projectRole.getIdentity() ) );
        }
        catch( Exception err )
        {
            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add ProjectRole";
    }
}