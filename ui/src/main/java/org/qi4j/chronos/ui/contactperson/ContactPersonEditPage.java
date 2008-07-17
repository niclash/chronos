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

import java.util.Iterator;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.injection.scope.Uses;
import org.qi4j.library.general.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContactPersonEditPage extends ContactPersonAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ContactPersonEditPage.class );

    private static final String SUBMIT_BUTTON = "editPageSubmitButton";
    private static final String TITLE_LABEL = "editPageTitleLabel";
    private static final String UPDATE_SUCCESS = "updateSuccessful";
    private static final String UPDATE_FAIL = "updateFailed";

    public ContactPersonEditPage( @Uses Page basePage, final @Uses IModel<ContactPerson> contactPersonModel )
    {
        super( basePage, contactPersonModel );

/*
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( contactPersonId, ContactPersonEntityComposite.class );
                    }
                }
            )
        );
*/
    }

    public void onSubmitting()
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            final ContactPerson contactPerson = (ContactPerson) getDefaultModelObject();
            contactPerson.contacts().clear();

            for( Contact contact : contactList )
            {
                contactPerson.contacts().add( contact );
            }
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            logInfoMsg( getString( UPDATE_SUCCESS ) );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( getString( UPDATE_FAIL, new Model( err ) ) );
            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }

/*
    public AbstractUserLoginPanel getLoginUserAbstractPanel( String id, IModel<Login> loginModel )
    {
        if( userLoginEditPanel == null )
        {
            userLoginEditPanel = new UserLoginEditPanel( id, loginModel );
        }

        return userLoginEditPanel;
    }
*/

    public String getSubmitButtonValue()
    {
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }

    public Iterator<Contact> getInitContactIterator()
    {
        return getContactPerson().contacts().iterator();
    }

    public abstract ContactPerson getContactPerson();
}
