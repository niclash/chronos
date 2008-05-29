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
import org.apache.wicket.model.IModel;
import org.qi4j.library.general.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContactAddPage extends ContactAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ContactAddPage.class );

    private static final long serialVersionUID = 1L;

    public ContactAddPage( Page basePage, IModel<Contact> contact)
    {
        super( basePage, contact );
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
        try
        {
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
