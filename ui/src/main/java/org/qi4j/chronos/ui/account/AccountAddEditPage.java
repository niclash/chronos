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
import org.apache.wicket.Localizer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.ui.address.AddressAddEditPanel;
import org.qi4j.chronos.ui.common.model.NameModel;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.service.account.AccountService;

@AuthorizeInstantiation( SystemRole.SYSTEM_ADMIN )
public abstract class AccountAddEditPage extends AddEditBasePage
{
    private static final String ACCOUNT_NAME_NOT_UNIQUE = "accountNameNotUnique";
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

    protected void bindPropertyModel( IModel iModel )
    {
        nameField.setModel( new NameModel( iModel ) );
        referenceField.setModel( new CustomCompositeModel( iModel, "reference" ) );
        addressAddEditPanel.bindPropertyModel( new CustomCompositeModel( iModel, "address" ) );
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

        if( !getAccountService().isUnique( (Account) getModelObject() ) )
        {
            Localizer localizer = getLocalizer();
            error( localizer.getString( ACCOUNT_NAME_NOT_UNIQUE, this, new Model( new CustomCompositeModel( getModel(), "name" ) ),
                "Account name " + nameField.getModelObjectAsString() + " is not unique!!!" ) );
            isRejected = true;
        }
        
        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    /**
     * Query the account service.
     * TODO kamil: might consider getting the service from somewhere else, ChronosWebApp maybe?
     * @return
     */
    protected AccountService getAccountService()
    {
        return ChronosSession.get().getAccountService();
    }

/*
    @Override public boolean isVersioned()
    {
        return false;
    }

    @Override protected void setHeaders( WebResponse response)
    {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, max-age=0, must-revalidate, no-store");
    }
*/

    public abstract void onSubmitting();
}
