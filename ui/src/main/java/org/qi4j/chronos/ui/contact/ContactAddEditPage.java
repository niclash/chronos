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
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.library.general.model.Contact;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class ContactAddEditPage extends AddEditBasePage
{
    private MaxLengthTextField valueField;
    private MaxLengthTextField contactTypeField;

    public ContactAddEditPage( Page basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        valueField = new MaxLengthTextField( "valueField", "Value", Contact.CONTACT_VALUE_LEN );
        contactTypeField = new MaxLengthTextField( "contactTypeField", "Contact Type", Contact.CONTACT_TYPE_LEN );

        form.add( valueField );
        form.add( contactTypeField );
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( valueField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }
        

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public void assignFieldValueToContact( ContactComposite contact )
    {
        contact.setContactValue( valueField.getText() );
        contact.setContactType( contactTypeField.getText() );
    }

    public void assignContactToFieldValue( ContactComposite contact )
    {
        valueField.setText( contact.getContactValue() );
        contactTypeField.setText( contact.getContactType() );
    }

    public abstract void onSubmitting();

    public abstract ContactPersonEntityComposite getContactPerson();
}
