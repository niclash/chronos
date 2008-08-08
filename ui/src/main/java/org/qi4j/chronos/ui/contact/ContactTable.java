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

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Contact;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasContacts;
import org.qi4j.chronos.model.composites.ContactEntity;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ContactTable extends ActionTable<Contact>
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( ContactTable.class );

    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_FAIL = "deleteFailed";

    private static final String[] COLUMN_NAMES = { "Contact", "Contact Type", "" };

    private static final String WICKET_ID_EDIT_LINK = "editLink";
    private static final String WICKET_ID_CONTACT = "contact";
    private static final String WICKET_ID_CONTACT_TYPE = "contactType";

    public ContactTable( String id, IModel<HasContacts> hasContacts, ContactDataProvider dataProvider )
    {
        super( id, hasContacts, dataProvider, COLUMN_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction( new DeleteAction<ContactEntity>( getString( DELETE_ACTION ) )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<ContactEntity> contacts )
            {
                handleDeleteAction( contacts );

                info( getString( DELETE_SUCCESS ) );
            }
        }
        );
    }

    private void handleDeleteAction( final List<ContactEntity> contacts )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            final ContactPerson contactPerson = (ContactPerson) getDefaultModelObject();

            for( ContactEntity contact : contacts )
            {
                contactPerson.contacts().remove( contact );
                unitOfWork.remove( contact );
            }

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    protected void authorizingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    public void populateItems( Item<org.qi4j.chronos.model.Contact> item )
    {
        org.qi4j.chronos.model.Contact contact = item.getModelObject();

        item.add( new Label( WICKET_ID_CONTACT, contact.contactValue().get() ) );
        item.add( new Label( WICKET_ID_CONTACT_TYPE, contact.contactType().get() ) );

        SimpleLink simpleLink = createEditLink( contact.identity().get() );

        item.add( simpleLink );
    }

    public void populateItems( Item item, IModel iModel )
    {
        final Contact contact = (Contact) iModel.getObject();
        final String contactId = contact.identity().get();

        item.add( new Label( WICKET_ID_CONTACT, contact.contactValue().get() ) );
        item.add( new Label( WICKET_ID_CONTACT_TYPE, contact.contactType().get() ) );

        SimpleLink simpleLink = createEditLink( contactId );

        item.add( simpleLink );
    }

    private SimpleLink createEditLink( final String contactId )
    {
        return new SimpleLink( WICKET_ID_EDIT_LINK, "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                //TODO
//                ContactEditPage editPage = new ContactEditPage( (BasePage) this.getPage(), contactId );
//
//                setResponsePage( editPage );
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }
}
