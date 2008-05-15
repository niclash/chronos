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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.ContactEntityComposite;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.relationship.RelationshipOptionPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.library.general.model.Contact;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class ContactPersonAddEditPage extends AddEditBasePage
{
    private UserAddEditPanel userAddEditPanel;
    private RelationshipOptionPanel relationshipOptionPanel;
    private List<MaxLengthTextField> contactValueFieldList;
    private List<MaxLengthTextField> contactTypeFieldList;
    private SubmitLink newContactLink;
    private ListView contactListView;
    protected List<Contact> contactList;
    private static final String DUPLICATE_ENTRY = "duplicateContacts";

    private transient UnitOfWork sharedUnitOfWork;

    public ContactPersonAddEditPage( Page goBackPage )
    {
        super( goBackPage );
    }

    public void initComponent( Form form )
    {
        final UnitOfWork unitOfWork = getUnitOfWork();
        setSharedUnitOfWork( unitOfWork );

        contactList = getInitContactList();

        contactValueFieldList = new ArrayList<MaxLengthTextField>();
        contactTypeFieldList = new ArrayList<MaxLengthTextField>();

        newContactLink = new SubmitLink( "newContactLink" )
        {
            public void onSubmit()
            {
                handleNewContact( unitOfWork );
            }
        };

        contactListView = new ListView( "contactListView", Arrays.asList( new Integer[contactList.size()] ) )
        {
            protected void populateItem( ListItem item )
            {
                final int index = item.getIndex();
                final Contact contact = contactList.get( index );

                final MaxLengthTextField contactValueField;
                final MaxLengthTextField contactTypeField;

                contactValueField =
                    new MaxLengthTextField( "contactValueField", "Contact Value", Contact.CONTACT_VALUE_LEN );
                contactTypeField =
                    new MaxLengthTextField( "contactTypeField", "Contact Type", Contact.CONTACT_TYPE_LEN );

                contactValueFieldList.add( contactValueField );
                contactTypeFieldList.add( contactTypeField );

                // bind model
                final IModel iModel = new CompoundPropertyModel( contact );
                contactValueField.setModel( new CustomCompositeModel( iModel, "contactValue" ) );
                contactTypeField.setModel( new CustomCompositeModel( iModel, "contactType" ) );

                SubmitLink deleteContact = new SubmitLink( "deleteContactLink" )
                {
                    public void onSubmit()
                    {
                        removeContact( index, unitOfWork );
                    }
                };

                item.add( contactTypeField );
                item.add( contactValueField );
                item.add( deleteContact );
            }
        };

        relationshipOptionPanel = new RelationshipOptionPanel( "relationshipOptionPanel" )
        {
            public Customer getCustomer()
            {
                return ContactPersonAddEditPage.this.getCustomer();
            }

            public UnitOfWork getSharedUnitOfWork()
            {
                return ContactPersonAddEditPage.this.getSharedUnitOfWork();
            }
        };

        userAddEditPanel = new UserAddEditPanel( "userAddEditPanel", true )
        {
            public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
            {
                return ContactPersonAddEditPage.this.getLoginUserAbstractPanel( id );
            }

            public Iterator<SystemRole> getInitSelectedRoleList()
            {
                return ContactPersonAddEditPage.this.getInitSelectedRoleList();
            }
        };

        form.add( newContactLink );
        form.add( contactListView );
        form.add( relationshipOptionPanel );
        form.add( userAddEditPanel );
    }

    private void removeContact( int index, final UnitOfWork unitOfWork )
    {
        if( contactList.size() == 0 )
        {
            return;
        }

        final Contact contact = contactList.remove( index );
        contactTypeFieldList.remove( index );
        contactValueFieldList.remove( index );

        unitOfWork.remove( contact );

        updateContactListView();
    }

    private void handleNewContact( final UnitOfWork unitOfWork )
    {
        addNewContact( unitOfWork );

        updateContactListView();
    }

    private void addNewContact( final UnitOfWork unitOfWork )
    {
        final Contact contact = unitOfWork.newEntityBuilder( ContactEntityComposite.class ).newInstance();
        contactList.add( contact );
    }

    private void updateContactListView()
    {
        contactListView.modelChanging();
        contactListView.setList( Arrays.asList( new Integer[ contactList.size() ] ) );
        contactListView.modelChanged();
    }

    protected void bindPropertyModel( IModel iModel )
    {
        userAddEditPanel.bindPropertyModel( iModel );
        relationshipOptionPanel.bindModel( new CustomCompositeModel( iModel, "relationship" ) );
    }

    protected RelationshipOptionPanel getRelationshipOptionPanel()
    {
        return this.relationshipOptionPanel;
    }

    public Iterator<SystemRole> getInitSelectedRoleList()
    {
        List<SystemRole> emptyList = Collections.emptyList();
        return emptyList.iterator();
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( userAddEditPanel.checkIsNotValidated() )
        {
            isRejected = true;
        }

        if( relationshipOptionPanel.checkIfNotValidated() )
        {
            isRejected = true;
        }

        if( areContactsNotValidated() || isHasDuplicateContact() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    private boolean isHasDuplicateContact()
    {
        for( int i = 0; i < contactList.size() - 1; i++ )
        {
            final Contact contact = contactList.get( i );

            for( int j = i + 1; j < contactList.size(); j++ )
            {
                final Contact contactOther = contactList.get( j );

                if( isEqualValue( contact, contactOther ) )
                {
                    error( getString( DUPLICATE_ENTRY ) );

                    return true;
                }
            }
        }

        return false;
    }

    private boolean isEqualValue( Contact contact, Contact contactOther )
    {
        return contact.contactType().get().equals( contactOther.contactType().get() ) &&
               contact.contactValue().get().equals( contactOther.contactValue().get() );
    }

    private boolean areContactsNotValidated()
    {
        int index = 0;

        boolean isInvalidContactValue = false;
        boolean isInvalidContactType = false;

        for( MaxLengthTextField contactValueFied : contactValueFieldList )
        {
            MaxLengthTextField contactTypeField = contactTypeFieldList.get( index );

            if( contactValueFied.checkIsEmptyOrInvalidLength() )
            {
                isInvalidContactValue = true;
            }

            if( contactTypeField.checkIsEmptyOrInvalidLength() )
            {
                isInvalidContactType = true;
            }

            if( isInvalidContactValue && isInvalidContactType )
            {
                break;
            }

            index++;
        }

        return isInvalidContactType || isInvalidContactValue;
    }

    private List<Contact> getInitContactList()
    {
        List<Contact> contactList = new ArrayList<Contact>();

        Iterator<Contact> contactIterator = getInitContactIterator();

        while( contactIterator.hasNext() )
        {
            contactList.add( contactIterator.next() );
        }

        return contactList;
    }

    public UnitOfWork getSharedUnitOfWork()
    {
        return sharedUnitOfWork;
    }

    public void setSharedUnitOfWork( UnitOfWork sharedUnitOfWork )
    {
        this.sharedUnitOfWork = sharedUnitOfWork;
    }

    public abstract Iterator<Contact> getInitContactIterator();

    public abstract Customer getCustomer();

    public abstract void onSubmitting();

    public abstract LoginUserAbstractPanel getLoginUserAbstractPanel( String id );
}
