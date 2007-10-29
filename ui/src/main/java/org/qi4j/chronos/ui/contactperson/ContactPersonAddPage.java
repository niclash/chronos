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
package org.qi4j.chronos.ui.contactperson;

import java.util.Collections;
import java.util.Iterator;
import org.apache.wicket.Page;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.LoginComposite;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.service.ContactPersonService;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserAddPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContactPersonAddPage extends ContactPersonAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ContactPersonAddPage.class );

    private LoginUserAddPanel loginUserAddPanel;

    public ContactPersonAddPage( Page basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
        ContactPersonService contactPersonService = getServices().getContactPersonService();

        ContactPersonEntityComposite contactPerson = contactPersonService.newInstance( ContactPersonEntityComposite.class );

        LoginComposite login = ChronosWebApp.newInstance( LoginComposite.class );

        contactPerson.setLogin( login );

        assignFieldValueToContactPerson( contactPerson );

        SystemRoleComposite contactPersonSystemRole = getServices().getSystemRoleService().getSystemRoleByName( SystemRole.CONTACT_PERSON );

        contactPerson.addSystemRole( contactPersonSystemRole );

        try
        {
            CustomerEntityComposite customer = getCustomer();

            customer.addContactPerson( contactPerson );

            CustomerService customerService = getServices().getCustomerService();

            customerService.update( customer );

            logInfoMsg( "Contact Person is added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage() );
        }
    }

    public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
    {
        if( loginUserAddPanel == null )
        {
            loginUserAddPanel = new LoginUserAddPanel( id );
        }

        return loginUserAddPanel;
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "New Contact Person";
    }

    public Iterator<ContactComposite> getInitContactIterator()
    {
        return Collections.EMPTY_LIST.iterator();
    }
}
