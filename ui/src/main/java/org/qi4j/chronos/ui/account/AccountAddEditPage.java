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

import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.ui.address.AddressAddEditPanel;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;

@AuthorizeInstantiation( SystemRole.SYSTEM_ADMIN )
public abstract class AccountAddEditPage extends AddEditBasePage
{
    protected MaxLengthTextField nameField;
    protected MaxLengthTextField referenceField;

    protected AddressAddEditPanel addressAddEditPanel;

    public AccountAddEditPage( Page goBackPage )
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

    protected void assignFieldValueToAccount( Account account )
    {
        account.name().set( nameField.getText() );
        account.reference().set( referenceField.getText() );

        addressAddEditPanel.assignFieldValueToAddress( account );
    }

    protected void assignAccountToFieldValue( Account account )
    {
        nameField.setText( account.name().get() );
        referenceField.setText( account.reference().get() );
        addressAddEditPanel.assignAddressToFieldValue( account );
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
