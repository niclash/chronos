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
import java.util.ArrayList;
import org.apache.wicket.Page;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeEntityComposite;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectAddPage extends ProjectAddEditPage
{
//    private @Structure UnitOfWorkFactory factory;
    
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectAddPage.class );

    public ProjectAddPage( final @Uses Page basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
        ProjectService projectService = ChronosWebApp.getServices().getProjectService();

        Project project = projectService.newInstance( ProjectEntityComposite.class );

        project.actualTime().set( ChronosWebApp.newInstance( TimeRangeEntityComposite.class ) );
        project.estimateTime().set( ChronosWebApp.newInstance( TimeRangeEntityComposite.class ) );

        try
        {
            assignFieldValuesToProject( project );

            Account account = getAccount();

            account.projects().add( project );

            // TODO migrate
//            ChronosWebApp.getServices().getAccountService().update( account );

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

    public Iterator<ContactPerson> getInitSelectedContactPersonList()
    {
        return Collections.EMPTY_LIST.iterator();
    }

    public List<PriceRateSchedule> getAvailablePriceRateSchedule()
    {
        return new ArrayList<PriceRateSchedule>( getAccount().priceRateSchedules() );
            // TODO migrate
//        return Collections.EMPTY_LIST;
    }

    public List<PriceRateSchedule> getAvailablePriceRateScheduleChoice()
    {
        for( Customer customer : getAccount().customers() )
        {
            if( customerChoice.getChoice().getId().equals( ( (Identity) customer).identity().get() ) )
            {
                return new ArrayList<PriceRateSchedule>( customer.priceRateSchedules() );
            }
        }

        return Collections.EMPTY_LIST;
        // TODO migrate
/*
        Customer customer = getCustomerService().get( customerChoice.getChoice().getId() );

        return ChronosWebApp.getServices().getPriceRateScheduleService().findAll( customer );
*/
    }
}
