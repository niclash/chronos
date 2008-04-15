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
package org.qi4j.chronos.ui.projectassignee;

import java.util.List;
import java.util.ArrayList;
import org.apache.wicket.Page;
import org.qi4j.chronos.service.ProjectAssigneeService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProjectAssigneeAddPage extends ProjectAssigneeAddEditPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectAssigneeAddPage.class );

    public ProjectAssigneeAddPage( Page basePage )
    {
        super( basePage );
    }

    public void onsubmitting()
    {
        ProjectAssigneeService service = ChronosWebApp.getServices().getProjectAssigneeService();

        try
        {
            ProjectAssignee projectAssignee = service.newInstance( ProjectAssigneeEntityComposite.class );

            assignFieldValueToProjectAssignee( projectAssignee );

            Project project = getProject();

            project.projectAssignees().add( projectAssignee );

            // TODO migrate
//            ChronosWebApp.getServices().getProjectService().update( project );

            logInfoMsg( "Project Assignee is added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public List<PriceRate> getAvailablePriceRates()
    {
        return new ArrayList<PriceRate>(0);
          // TODO migrate
//        return ChronosWebApp.getServices().getPriceRateService().findAll( getProject().priceRateSchedule().get() );
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Project Assignee";
    }
}
