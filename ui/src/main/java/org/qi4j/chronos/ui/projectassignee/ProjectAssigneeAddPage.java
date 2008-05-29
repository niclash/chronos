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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.ProjectAssignee;

public abstract class ProjectAssigneeAddPage extends ProjectAssigneeAddEditPage
{
    private static final long serialVersionUID = 1L;

    public ProjectAssigneeAddPage( Page basePage, IModel<ProjectAssignee> model )
    {
        super( basePage, model );
    }

    public void onsubmitting( IModel<ProjectAssignee> model )
    {
//        ProjectAssigneeService service = ChronosWebApp.getServices().getProjectAssigneeService();
//
//        try
//        {
//            ProjectAssignee projectAssignee = service.newInstance( ProjectAssigneeEntityComposite.class );
//
//            assignFieldValueToProjectAssignee( projectAssignee );
//
//            Project project = getProject();
//
//            project.projectAssignees().add( projectAssignee );
//
//            // TODO migrate
////            ChronosWebApp.getServices().getProjectService().update( project );
//
//            logInfoMsg( "Project Assignee is added successfully." );
//
//            divertToGoBackPage();
//        }
//        catch( Exception err )
//        {
//            logErrorMsg( err.getMessage() );
//            LOGGER.error( err.getMessage(), err );
//        }
    }

    public List<PriceRate> getAvailablePriceRates()
    {
        return new ArrayList<PriceRate>( 0 );
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
