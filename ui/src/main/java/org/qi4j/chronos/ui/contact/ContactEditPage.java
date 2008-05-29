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
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.library.general.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactEditPage extends ContactAddEditPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( ContactEditPage.class );

    public ContactEditPage( Page basePage, IModel<Contact> contact )
    {
        super( basePage, contact );
    }

    public void onSubmitting( IModel<Contact> contact )
    {
        try
        {
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            logInfoMsg( "Contact was saved successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( err.getMessage() );

            LOGGER.error( err.getLocalizedMessage(), err );
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
}
