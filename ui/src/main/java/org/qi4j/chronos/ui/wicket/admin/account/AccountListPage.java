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
package org.qi4j.chronos.ui.wicket.admin.account;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import static org.qi4j.chronos.model.SystemRole.SYSTEM_ADMIN;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.injection.scope.Structure;
import org.qi4j.object.ObjectBuilder;
import org.qi4j.object.ObjectBuilderFactory;

@AuthorizeInstantiation( SYSTEM_ADMIN )
public class AccountListPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_ACCOUNT_TABLE = "accountTable";
    private static final String WICKET_ID_NEW_ACCOUNT_LINK = "newAccountLink";
    private static final String WICKET_ID_FEEDBACK_PANEL = "feedbackPanel";

    public AccountListPage( @Structure ObjectBuilderFactory anOBF )
    {
        add( new Link( WICKET_ID_NEW_ACCOUNT_LINK )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick()
            {
                //TODO accountAddPage
                final PageParameters param = new PageParameters();
                param.put( AccountListPage.class.getName(), AccountListPage.this );

                setResponsePage( newPage( AccountAddPage.class, param ) );
            }
        } );


        ObjectBuilder<AccountTable> accountTableBuilder = anOBF.newObjectBuilder( AccountTable.class );
        accountTableBuilder.use( WICKET_ID_ACCOUNT_TABLE );
        AccountTable accountTable = accountTableBuilder.newInstance();
        add( accountTable );

        add( new FeedbackPanel( WICKET_ID_FEEDBACK_PANEL ) );
    }
}
