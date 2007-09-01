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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;

public class AccountListPage extends LeftMenuNavPage
{
    private DropDownChoice customerChoice;

    public AccountListPage()
    {
        initComponents();
    }

    private void initComponents()
    {
        customerChoice = new DropDownChoice( "customerChoice" );

        customerChoice.add( new AjaxFormComponentUpdatingBehavior( "onchange" )
        {
            protected void onUpdate( AjaxRequestTarget target )
            {
                handleCustomerChanged();
            }
        } );

        add( customerChoice );

        add( new Link( "newAccountLink" )
        {
            public void onClick()
            {
                setResponsePage( new AccountAddPage( AccountListPage.this ) );
            }
        } );

        add( new FeedbackPanel( "feedbackPanel" ) );

        AccountTable accountTable = new AccountTable( "accountTable" );

        add( accountTable );
    }

    private void handleCustomerChanged()
    {
        //TODO
    }

}