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
package org.qi4j.chronos.ui.project;

import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.ContactPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.Page;

public abstract class ProjectEditPage extends ProjectAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectEditPage.class );

    public ProjectEditPage( Page basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        assignProjectToFieldValues( getProject() );
    }

    public void onSubmitting()
    {
        Project project = getProject();

        try
        {
            assignFieldValuesToProject( project );

            // TODO migrate
//            ChronosWebApp.getServices().getProjectService().update( project );

            logInfoMsg( "Project updated successfully." );

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
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Project";
    }

    public Iterator<ContactPerson> getInitSelectedContactPersonList()
    {
        return getProject().contactPersons().iterator();
    }

    public List<PriceRateSchedule> getAvailablePriceRateScheduleChoice()
    {
        Customer customer = getCustomerService().get( customerChoice.getChoice().getId() );

        List<PriceRateSchedule> list = ChronosWebApp.getServices().getPriceRateScheduleService().findAll( customer );

        list.add( getProject().priceRateSchedule().get() );

        return list;
    }

    public abstract Project getProject();
}
