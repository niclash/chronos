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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.customer.CustomerDetailPanel;

public class AccountDetailPage extends LeftMenuNavPage
{
    private WebPage returnPage;

    private String accountId;

    public AccountDetailPage( WebPage returnPage, String accountId )
    {
        this.returnPage = returnPage;
        this.accountId = accountId;

        initComponents();
    }


    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new AccountDetailForm( "accountDetailForm" ) );
    }

    private class AccountDetailForm extends Form
    {
        private Button goButton;
        private CustomerDetailPanel customerDetailPanel;

        public AccountDetailForm( String id )
        {
            super( id );

            AccountEntityComposite account = getAccount();

            goButton = new Button( "submitButton", new Model( "Return" ) );

            customerDetailPanel = new CustomerDetailPanel( "customerDetailPanel", account );

            add( customerDetailPanel );
            add( goButton );
        }

        protected void delegateSubmit( IFormSubmittingComponent submittingButton )
        {
            if( submittingButton == goButton )
            {
                setResponsePage( returnPage );
            }
            else
            {
                throw new IllegalArgumentException( submittingButton + " not handled yet." );
            }
        }
    }

}
