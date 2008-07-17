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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;

public abstract class AccountAbstractPage extends LeftMenuNavPage
{
    public AccountAbstractPage()
    {
        initComponents();
    }

    private void initComponents()
    {
        add( new AccountForm( "accountForm" ) );

        final String titleLabel = getTitleLabel();

        add( new Label( "titleLabel", titleLabel ) );
    }

    protected class AccountForm extends Form
    {
        protected MaxLengthTextField nameField;

        private Button submitButton;
        private Button cancelButton;

        public AccountForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            nameField = new MaxLengthTextField( "name", "Account Name", Account.NAME_LEN );

            add( nameField );

            String buttonValue = getSubmitButtonValue();
            submitButton = new Button( "submitButton", new Model( buttonValue ) );
            cancelButton = new Button( "cancelButton", new Model( "Cancel" ) );

            add( submitButton );
            add( cancelButton );
        }

        public void delegateSubmit( IFormSubmittingComponent submittingComponent )
        {
            if( submittingComponent == submitButton )
            {
                handleSubmit();
            }
            else if( submittingComponent == cancelButton )
            {
                setResponsePage( AccountListPage.class );
            }
            else
            {
                throw new IllegalArgumentException( submittingComponent + " is not handled yet." );
            }
        }

        private void handleSubmit()
        {
            boolean isRejected = false;

            if( nameField.checkIsEmptyOrInvalidLength() )
            {
                isRejected = true;
            }

            if( isRejected )
            {
                return;
            }

            onSubmitting();
        }
    }

    public abstract void onSubmitting();

    public abstract String getSubmitButtonValue();

    public abstract String getTitleLabel();
}
