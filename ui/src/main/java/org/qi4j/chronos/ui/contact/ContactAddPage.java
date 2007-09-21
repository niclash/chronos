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
package org.qi4j.chronos.ui.contact;

import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContactAddPage extends ContactAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ContactAddPage.class );

    public ContactAddPage( BasePage basePage )
    {
        super( basePage );
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Contact";
    }

    public void onSubmitting()
    {
        ContactComposite contact = ChronosWebApp.newInstance( ContactComposite.class );

        try
        {
            assignContactToFieldValue( contact );

            ContactPersonEntityComposite contactPerson = getContactPerson();

            contactPerson.addContact( contact );

            ContactPersonService service = ChronosWebApp.getServices().getContactPersonService();

            service.update( contactPerson );

            logInfoMsg( "Contact is added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );

            LOGGER.error( err.getMessage(), err );
        }
    }
}
