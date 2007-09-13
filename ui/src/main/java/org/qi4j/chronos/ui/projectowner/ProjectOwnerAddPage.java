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

import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressComposite;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.ProjectOwnerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.library.general.model.composites.CityComposite;
import org.qi4j.library.general.model.composites.CountryComposite;
import org.qi4j.library.general.model.composites.StateComposite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProjectOwnerAddPage extends ProjectOwnerAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectOwnerAddPage.class );

    public ProjectOwnerAddPage( BasePage basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
        ProjectOwnerService service = ChronosWebApp.getServices().getProjectOwnerService();

        ProjectOwnerEntityComposite projectOwner = service.newInstance( ProjectOwnerEntityComposite.class );

        try
        {
            AddressComposite address = ChronosWebApp.newInstance( AddressComposite.class );
            CityComposite city = ChronosWebApp.newInstance( CityComposite.class );
            StateComposite state = ChronosWebApp.newInstance( StateComposite.class );
            CountryComposite country = ChronosWebApp.newInstance( CountryComposite.class );

            address.setCity( city );

            city.setState( state );
            city.setCountry( country );

            projectOwner.setAddress( address );

            customerAddEditPanel.assignFieldValueToCustomer( projectOwner );

            AccountService accountService = ChronosWebApp.getServices().getAccountService();

            AccountEntityComposite account = getAccount();

            account.addProjectOwner( projectOwner );

            accountService.update( account );

            logInfoMsg( "Project Owner is added successfully." );

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
        return "Add Project Owner";
    }
}
