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
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Contact;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.ContactEntity;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.relationship.RelationshipOptionPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class ContactPersonAddEditPage extends AddEditBasePage
{
    private UserAddEditPanel userAddEditPanel;
    private RelationshipOptionPanel relationshipOptionPanel;
    private List<TextField> contactValueFieldList;
    private List<TextField> contactTypeFieldList;
    private SubmitLink newContactLink;
    private ListView contactListView;
    protected List<Contact> contactList;
    private static final String DUPLICATE_ENTRY = "duplicateContacts";

    public ContactPersonAddEditPage( Page goBackPage, IModel<ContactPerson> contactPersonModel )
    {
        super( goBackPage, contactPersonModel );
    }

    public void initComponent( Form form )
    {
        contactList = getInitContactList();

        contactValueFieldList = new ArrayList<TextField>();
        contactTypeFieldList = new ArrayList<TextField>();

        newContactLink = new SubmitLink( "newContactLink" )
        {
            public void onSubmit()
            {
                handleNewContact();
            }
        };

        contactListView = new ListView( "contactListView", Arrays.asList( new Integer[contactList.size()] ) )
        {
            private static final long serialVersionUID = 1L;

            protected void populateItem( ListItem item )
            {
                final int index = item.getIndex();
                final Contact contact = contactList.get( index );

                final TextField contactValueField;
                final TextField contactTypeField;

                contactValueField =
                    new TextField( "contactValueField" );
                contactTypeField =
                    new TextField( "contactTypeField" );

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
                        removeContact( index );
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
        };

        userAddEditPanel = new UserAddEditPanel( "userAddEditPanel", form.getModel(), true )
        {
/*
            public AbstractUserLoginPanel getLoginUserAbstractPanel( String id )
            {
                return ContactPersonAddEditPage.this.getLoginUserAbstractPanel( id );
            }
*/

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

    private void removeContact( int index )
    {
        if( contactList.size() == 0 )
        {
            return;
        }

        final Contact contact = contactList.remove( index );
        contactTypeFieldList.remove( index );
        contactValueFieldList.remove( index );

        ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().remove( contact );

        updateContactListView();
    }

    private void handleNewContact()
    {
        addNewContact();

        updateContactListView();
    }

    private void addNewContact()
    {
        final Contact contact = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().newEntityBuilder( ContactEntity.class ).newInstance();
        contactList.add( contact );
    }

    private void updateContactListView()
    {
        contactListView.modelChanging();
        contactListView.setList( Arrays.asList( new Integer[contactList.size()] ) );
        contactListView.modelChanged();
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

    public void handleSubmitClicked()
    {
        boolean isRejected = false;

/*
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
*/

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

/*
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
*/

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

    public abstract Iterator<Contact> getInitContactIterator();

    public abstract Customer getCustomer();

    public abstract void onSubmitting();

//    public abstract AbstractUserLoginPanel getLoginUserAbstractPanel( String id );
}
