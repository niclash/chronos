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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserAddPanel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.library.general.model.Contact;
import org.qi4j.library.general.model.GenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContactPersonAddPage extends ContactPersonAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ContactPersonAddPage.class );
    private LoginUserAddPanel loginUserAddPanel;
    private static final String ADD_SUCCESS = "addSuccessful";
    private static final String ADD_FAIL = "addFailed";
    private static final String SUBMIT_BUTTON = "addPageSubmitButton";
    private static final String TITLE_LABEL = "addPageTitleLabel";

    public ContactPersonAddPage( @Uses Page basePage )
    {
        super( basePage );

        validateNotNull( "basePage", basePage );

        bindModel();
    }

    private void bindModel()
    {
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
                        final ContactPerson contactPerson =
                            unitOfWork.newEntityBuilder( ContactPersonEntityComposite.class ).newInstance();
                        final Login contactPersonLogin =
                            unitOfWork.newEntityBuilder( LoginEntityComposite.class ).newInstance();
                        final SystemRole contactPersonRole =
                            unitOfWork.find( SystemRole.CONTACT_PERSON, SystemRoleEntityComposite.class );
                        contactPerson.gender().set( GenderType.MALE );
                        contactPersonLogin.isEnabled().set( true );
                        contactPerson.login().set( contactPersonLogin );
                        contactPerson.systemRoles().add( contactPersonRole );
                        contactPerson.relationship().set( getRelationshipOptionPanel().getRelationshipList().get( 0 ) );

                        return contactPerson;
                    }
                }
            )
        );

        bindPropertyModel( getModel() );
    }

    public void onSubmitting()
    {
        try
        {
            final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
            final Customer customer = unitOfWork.dereference( getCustomer() );
            final ContactPerson contactPerson = (ContactPerson) getModelObject();

            for( Contact contact : contactList )
            {
                contactPerson.contacts().add( contact );
            }

            customer.contactPersons().add( contactPerson );

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

            logInfoMsg( getString( ADD_SUCCESS ) );

            super.divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( getString( ADD_FAIL, new Model( err ) ) );
            LOGGER.error( err.getLocalizedMessage(), err );
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
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }

    public Iterator<Contact> getInitContactIterator()
    {
        return Collections.EMPTY_LIST.iterator();
    }
}
