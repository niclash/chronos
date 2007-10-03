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
package org.qi4j.chronos.ui.account;

import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.ui.address.AddressAddEditPanel;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextField;

public abstract class AccountAddEditPage extends AddEditBasePage
{
    protected MaxLengthTextField nameField;
    protected MaxLengthTextField referenceField;

    protected AddressAddEditPanel addressAddEditPanel;

    public AccountAddEditPage( BasePage goBackPage )
    {
        super( goBackPage );
    }

    public final void initComponent( Form form )
    {
        nameField = new MaxLengthTextField( "nameField", "Name", Customer.NAME_LEN );
        referenceField = new MaxLengthTextField( "referenceField", "Reference", Customer.REFERENCE_LEN );

        addressAddEditPanel = new AddressAddEditPanel( "addressAddEditPanel" );

        form.add( nameField );
        form.add( referenceField );
        form.add( addressAddEditPanel );
    }

    protected void assignFieldValueToAccount( AccountEntityComposite account )
    {
        account.setName( nameField.getText() );
        account.setReference( referenceField.getText() );

        addressAddEditPanel.assignFieldValueToAddress( account.getAddress() );
    }

    protected void assignAccountToFieldValue( AccountEntityComposite account )
    {
        nameField.setText( account.getName() );
        referenceField.setText( account.getReference() );
        addressAddEditPanel.assignAddressToFieldValue( account.getAddress() );
    }

    public final void handleSubmit()
    {
        boolean isRejected = false;

        if( nameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( referenceField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( addressAddEditPanel.checkIsNotValidated() )
        {
            isRejected = true;
        }
        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public abstract void onSubmitting();
}
