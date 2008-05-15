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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.ContactEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.library.general.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContactTable extends ActionTable<IModel, String>
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ContactTable.class );
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_FAIL = "deleteFailed";

    public ContactTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new SimpleDeleteAction<IModel>( getString( DELETE_ACTION ) )
            {
                public void performAction( List<IModel> contacts )
                {
                    handleDeleteAction( contacts );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
    }

    private void handleDeleteAction( final List<IModel> contacts )
    {
        final UnitOfWork unitOfWork = getUnitOfWork();
        try
        {
            final ContactPerson contactPerson = unitOfWork.dereference( getContactPerson() );
            for( IModel iModel : contacts )
            {
                final Contact contact = (Contact) iModel.getObject();
                contactPerson.contacts().remove( contact );
                unitOfWork.remove( contact );
            }
            unitOfWork.complete();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            unitOfWork.reset();

            error( getString( DELETE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    public AbstractSortableDataProvider<IModel, String> getDetachableDataProvider()
    {
        return new AbstractSortableDataProvider<IModel, String>()
        {
            public int getSize()
            {
                return ContactTable.this.getContactPerson().contacts().size();
            }

            public String getId( IModel t )
            {
                return ( (Identity) t.getObject() ).identity().get();
            }

            public IModel load( final String s )
            {
                return new CompoundPropertyModel(
                    new LoadableDetachableModel()
                    {
                        protected Object load()
                        {
                            return getUnitOfWork().find( s, ContactEntityComposite.class );
                        }
                    }
                );
            }

            public List<IModel> dataList( int first, int count )
            {
                List<IModel> models = new ArrayList<IModel>();
                for( final String contactId : ContactTable.this.dataList( first, count ) )
                {
                    models.add(
                        new CompoundPropertyModel(
                            new LoadableDetachableModel()
                            {
                                protected Object load()
                                {
                                    return getUnitOfWork().find( contactId, ContactEntityComposite.class );
                                }
                            }
                        )
                    );
                }
                return models;
            }
        };
    }

    protected void authorizatiingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }
    
    public void populateItems( Item item, IModel iModel )
    {
        final Contact contact = (Contact) iModel.getObject();
        final String contactId = ( (Identity) contact).identity().get();

        item.add( new Label( "contact", contact.contactValue().get() ) );
        item.add( new Label( "contactType", contact.contactType().get() ) );

        SimpleLink simpleLink = createEditLink( contactId );

        item.add( simpleLink );
    }

    private SimpleLink createEditLink( final String contactId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ContactEditPage editPage = new ContactEditPage( (BasePage) this.getPage(), contactId );

                setResponsePage( editPage );
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Contact", "Contact Type", "" );
    }

    public abstract ContactPerson getContactPerson();

    public abstract List<String> dataList( int first, int count );
}
