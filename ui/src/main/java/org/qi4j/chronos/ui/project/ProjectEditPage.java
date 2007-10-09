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
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
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
        ProjectEntityComposite project = getProject();

        try
        {
            assignFieldValuesToProject( project );

            ChronosWebApp.getServices().getProjectService().update( project );

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

    public Iterator<ContactPersonEntityComposite> getInitSelectedContactPersonList()
    {
        return getProject().contactPersonIterator();
    }

    public List<PriceRateScheduleComposite> getAvailablePriceRateScheduleChoice()
    {
        CustomerEntityComposite customer = getCustomerService().get( customerChoice.getChoice().getId() );

        List<PriceRateScheduleComposite> list = ChronosWebApp.getServices().getPriceRateScheduleService().findAll( customer );

        list.add( getProject().getPriceRateSchedule() );

        return list;
    }

    public abstract ProjectEntityComposite getProject();
}
