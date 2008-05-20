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
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.PriceRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProjectAssigneeEditPage extends ProjectAssigneeAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectAssigneeEditPage.class );

    public ProjectAssigneeEditPage( Page basePage )
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
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Project Assignee";
    }

    public void onsubmitting()
    {
        ProjectAssignee projectAssignee = getProjectAssignee();

        try
        {
            assignFieldValueToProjectAssignee( projectAssignee );

            // TODO migrate
//            ChronosWebApp.getServices().getProjectAssigneeService().update( projectAssignee );

            logInfoMsg( "Project assignee is updated successfully." );

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
        // TODO migrate
//        List<PriceRate> priceRateList = ChronosWebApp.getServices().getPriceRateService().
//            findAll( getProject().priceRateSchedule().get() );

        List<PriceRate> priceRateList = new ArrayList<PriceRate>();
        priceRateList.add( getProjectAssignee().priceRate().get() );

        return priceRateList;
    }

    public abstract ProjectAssignee getProjectAssignee();
}
