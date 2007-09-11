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
package org.qi4j.chronos.ui.projectowner;

import org.qi4j.chronos.model.ProjectOwner;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.service.ProjectOwnerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectOwnerEditPage extends ProjectOwnerAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectOwnerEditPage.class );

    private String projectOwnerId;

    public ProjectOwnerEditPage( BasePage basePage, String projectOwnerId, String accountId )
    {
        super( basePage, accountId );

        this.projectOwnerId = projectOwnerId;

        ProjectOwner projectOwner = ChronosWebApp.getServices().getProjectOwnerService().get( projectOwnerId );

        customerAddEditPanel.assignCustomerToFields( projectOwner );
    }

    public void onSubmitting()
    {
        try
        {
            ProjectOwnerService service = ChronosWebApp.getServices().getProjectOwnerService();

            ProjectOwnerEntityComposite projectOwner = service.get( projectOwnerId );

            service.update( projectOwner );

            logInfoMsg( "Project Owner is updated successfully." );

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
        return "Edit";
    }

    public String getTitleLabel()
    {
        return "Edit Project Owner";
    }
}
