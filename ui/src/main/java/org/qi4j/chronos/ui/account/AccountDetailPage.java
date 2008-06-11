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
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.ui.address.AddressDetailPanel;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.injection.scope.Uses;

public class AccountDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    public AccountDetailPage( final @Uses Page returnPage, @Uses IModel<Account> accountModel )
    {
        add( new FeedbackPanel( "feedbackPanel" ) );

        ChronosCompoundPropertyModel model = new ChronosCompoundPropertyModel( accountModel );
        setModel( model );

        TextField nameField = new TextField( "name" );
        TextField referenceField = new TextField( "reference" );

        IModel<Address> addressIModel = model.bind( "address" );

        AddressDetailPanel addressDetailPanel = new AddressDetailPanel( "address", addressIModel );

        Button<String> goButton = new Button<String>( "submitButton", new Model<String>( "Return" ) )
        {
            private static final long serialVersionUID = 1L;

            public void onSubmit()
            {
                setResponsePage( returnPage );
            }
        };

        add( nameField );
        add( referenceField );
        add( addressDetailPanel );
        add( goButton );
    }
}
