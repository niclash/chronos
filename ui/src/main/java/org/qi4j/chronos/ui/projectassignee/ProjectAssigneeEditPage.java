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
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProjectAssigneeEditPage extends ProjectAssigneeAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectAssigneeEditPage.class );

    public ProjectAssigneeEditPage( BasePage basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        assignProjectAssigneeToFieldValue( getProjectAssignee() );
    }

    public String getSubmitButtonValue()
    {
        return "Edit";
    }

    public String getTitleLabel()
    {
        return "Edit Project Assignee";
    }

    public void onsubmitting()
    {
        ProjectAssigneeEntityComposite projectAssignee = getProjectAssignee();

        try
        {
            assignFieldValueToProjectAssignee( projectAssignee );

            ChronosWebApp.getServices().getProjectAssigneeService().update( projectAssignee );

            logInfoMsg( "Project assignee is updated successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public List<PriceRateComposite> getAvailablePriceRates()
    {
        List<PriceRateComposite> priceRateList = ChronosWebApp.getServices().getPriceRateService().findAll( getProject().getPriceRateSchedule() );

        priceRateList.add( getProjectAssignee().getPriceRate() );

        return priceRateList;
    }

    public abstract ProjectAssigneeEntityComposite getProjectAssignee();
}
