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
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.relationship.RelationshipOptionPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.entity.association.SetAssociation;
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

    //TODO remove static
    private static List<ContactComposite> contactList;

    public ContactPersonAddEditPage( Page goBackPage )
    {
        super( goBackPage );
    }

    public void initComponent( Form form )
    {
        contactList = getInitContactList();

        contactValueFieldList = new ArrayList<MaxLengthTextField>();
        contactTypeFieldList = new ArrayList<MaxLengthTextField>();

        newContactLink = new SubmitLink( "newContactLink" )
        {
            public void onSubmit()
            {
                handleNewContact();
            }
        };

        contactListView = new ListView( "contactListView", Arrays.asList( new Integer[contactList.size()] ) )
        {
            protected void populateItem( ListItem item )
            {
                final int index = item.getIndex();
                ContactComposite contact = contactList.get( index );

                MaxLengthTextField contactValueField;
                MaxLengthTextField contactTypeField;

                if( contactValueFieldList.size() <= index )
                {
                    contactValueField = new MaxLengthTextField( "contactValueField", "Contact Value", Contact.CONTACT_VALUE_LEN );
                    contactTypeField = new MaxLengthTextField( "contactTypeField", "Contact Type", Contact.CONTACT_TYPE_LEN );

                    contactValueFieldList.add( contactValueField );
                    contactTypeFieldList.add( contactTypeField );

                    //init value
                    contactValueField.setText( contact.contactValue().get() );
                    contactTypeField.setText( contact.contactType().get() );
                }
                else
                {
                    contactValueField = contactValueFieldList.get( index );
                    contactTypeField = contactTypeFieldList.get( index );
                }

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
            public CustomerEntityComposite getCustomer()
            {
                return ContactPersonAddEditPage.this.getCustomer();
            }
        };

        userAddEditPanel = new UserAddEditPanel( "userAddEditPanel", true )
        {
            public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
            {
                return ContactPersonAddEditPage.this.getLoginUserAbstractPanel( id );
            }

            public Iterator<SystemRoleComposite> getInitSelectedRoleList()
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

        contactList.remove( index );

        contactTypeFieldList.remove( index );
        contactValueFieldList.remove( index );

        updateContactListView();
    }

    private void handleNewContact()
    {
        addNewPriceRate();

        updateContactListView();
    }

    private void addNewPriceRate()
    {
        ContactComposite contact = ChronosWebApp.newInstance( ContactComposite.class );

        contactList.add( contact );
    }

    private void updateContactListView()
    {
        contactListView.setList( Arrays.asList( new Integer[contactList.size()] ) );
    }

    protected void assignContactPersonToFieldValue( ContactPerson contactPerson )
    {
        userAddEditPanel.assignUserToFieldValue( contactPerson );

        relationshipOptionPanel.setSelectedRelationship( contactPerson.relationship().get() );
    }

    protected void assignFieldValueToContactPerson( ContactPerson contactPerson )
    {
        userAddEditPanel.assignFieldValueToUser( contactPerson );

        contactPerson.relationship().set( relationshipOptionPanel.getSelectedRelationship() );
        SetAssociation<ContactComposite> contacts = contactPerson.contacts();
        contacts.addAll( contactList );
    }

    public Iterator<SystemRoleComposite> getInitSelectedRoleList()
    {
        List<SystemRoleComposite> emptyList = Collections.emptyList();
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

        fillUpContact();

        onSubmitting();
    }

    private void fillUpContact()
    {
        int index = 0;

        for( ContactComposite contact : contactList )
        {
            MaxLengthTextField contactValueField = contactValueFieldList.get( index );
            MaxLengthTextField contactTypeField = contactTypeFieldList.get( index );

            contact.contactValue().set( contactValueField.getText() );
            contact.contactType().set( contactTypeField.getText() );

            index++;
        }
    }

    private boolean isHasDuplicateContact()
    {
        for( int i = 0; i < contactValueFieldList.size(); i++ )
        {
            MaxLengthTextField contactValueField = contactValueFieldList.get( i );
            MaxLengthTextField contactTypeField = contactTypeFieldList.get( i );

            for( int j = i + 1; j < contactValueFieldList.size(); j++ )
            {
                MaxLengthTextField contactValueField2 = contactValueFieldList.get( j );
                MaxLengthTextField contactTyoeField2 = contactTypeFieldList.get( j );

                if( contactValueField.getText().equals( contactValueField2.getText() )
                    && contactTypeField.getText().equals( contactTyoeField2.getText() ) )
                {
                    error( "Identical contact found! Please change it or remove it." );

                    return true;
                }
            }
        }

        return false;
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

    private List<ContactComposite> getInitContactList()
    {
        List<ContactComposite> contactList = new ArrayList<ContactComposite>();

        Iterator<ContactComposite> contactIterator = getInitContactIterator();

        while( contactIterator.hasNext() )
        {
            contactList.add( contactIterator.next() );
        }

        return contactList;
    }

    public abstract Iterator<ContactComposite> getInitContactIterator();

    public abstract CustomerEntityComposite getCustomer();

    public abstract void onSubmitting();

    public abstract LoginUserAbstractPanel getLoginUserAbstractPanel( String id );
}
