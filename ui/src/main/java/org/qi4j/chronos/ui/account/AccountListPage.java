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

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.PageParameters;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;

@AuthorizeInstantiation( SystemRole.SYSTEM_ADMIN )
public class AccountListPage extends LeftMenuNavPage
{
    public AccountListPage()
    {
        initComponents();
    }

    private void initComponents()
    {
        add( new Link( "newAccountLink" )
        {
            public void onClick()
            {
                final PageParameters param = new PageParameters();
                param.put( AccountListPage.class, AccountListPage.this );

                setResponsePage( getPageFactory().newPage( AccountAddPage.class, param ) );
            }
        } );

        add( new FeedbackPanel( "feedbackPanel" ) );

        AccountTable accountTable = new AccountTable( "accountTable" )
        {
            public Account getAccount()
            {
                return AccountListPage.this.getAccount();
            }
        };

        add( accountTable );
    }

}
