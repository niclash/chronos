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

import org.apache.wicket.Page;
import org.qi4j.chronos.service.ContactService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.library.general.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContactEditPage extends ContactAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ContactEditPage.class );

    public ContactEditPage( Page basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        Contact contact = getContact();

        assignContactToFieldValue( contact );
    }

    private ContactService getContactService()
    {
        return ChronosWebApp.getServices().getContactService();
    }

    public void onSubmitting()
    {
        try
        {
            Contact toBeUpdated = getContact();
            Contact old = getContact();

            assignFieldValueToContact( toBeUpdated );

            getContactService().update( getContactPerson(), old, toBeUpdated );

            info( "Contact is updated." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            LOGGER.error( err.getMessage(), err );
            err.printStackTrace();
        }
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Contact";
    }

    public abstract Contact getContact();
}
