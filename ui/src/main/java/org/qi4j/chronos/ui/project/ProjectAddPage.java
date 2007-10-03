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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeComposite;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectAddPage extends ProjectAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectAddPage.class );

    public ProjectAddPage( BasePage basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
        ProjectService projectService = ChronosWebApp.getServices().getProjectService();

        ProjectEntityComposite project = projectService.newInstance( ProjectEntityComposite.class );

        project.setActualTime( ChronosWebApp.newInstance( TimeRangeComposite.class ) );
        project.setEstimateTime( ChronosWebApp.newInstance( TimeRangeComposite.class ) );

        try
        {
            assignFieldValuesToProject( project );

            AccountEntityComposite account = getAccount();

            account.addProject( project );

            ChronosWebApp.getServices().getAccountService().update( account );

            logInfoMsg( "Project is added successfully." );

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
        return "Add";
    }

    public String getTitleLabel()
    {
        return "New Project";
    }

    public Iterator<ContactPersonEntityComposite> getInitSelectedContactPersonList()
    {
        return Collections.EMPTY_LIST.iterator();
    }

    public List<PriceRateScheduleComposite> getAvailablePriceRateSchedule()
    {
        return Collections.EMPTY_LIST;
    }

    public List<PriceRateScheduleComposite> getAvailablePriceRateScheduleChoice()
    {
        CustomerEntityComposite customer = getCustomerService().get( customerChoice.getChoice().getId() );

        return ChronosWebApp.getServices().getPriceRateScheduleService().findAll( customer );
    }
}
