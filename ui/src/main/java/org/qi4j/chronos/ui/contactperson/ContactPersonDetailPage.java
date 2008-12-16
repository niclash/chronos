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
package org.qi4j.chronos.ui.contactperson;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.user.UserDetailPanel;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;
import org.qi4j.api.injection.scope.Uses;

public class ContactPersonDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private Page basePage;

    public ContactPersonDetailPage( final @Uses Page basePage, IModel<ContactPerson> contactPersonModel )
    {
        this.basePage = basePage;

        ChronosCompoundPropertyModel<IModel<ContactPerson>> model = new ChronosCompoundPropertyModel<IModel<ContactPerson>>( contactPersonModel );

        setDefaultModel( model );

        add( new FeedbackPanel( "feedbackPanel" ) );

        TextField relationshipField = new TextField( "relationship.relationship" );
        UserDetailPanel userDetailPanel = new UserDetailPanel( "userDetailPanel", model.getObject() );
        Link goBackLink = new Link( "goBackLink" )
        {
            public void onClick()
            {
                setResponsePage( basePage );
            }
        };

        List<ITab> tabs = new ArrayList<ITab>();

/*
        tabs.add(
            new ContactTab()
            {
                public ContactPerson getContactPerson()
                {
                    return (ContactPerson) iModel.getObject();
                }
            }
        );
*/

        ChronosDetachableModel<Account> account = new ChronosDetachableModel<Account>( getAccount() );

        tabs.add( new ProjectTab( "Project", account ) );

        TabbedPanel tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

        add( tabbedPanel );
        add( relationshipField );
        add( userDetailPanel );
        add( goBackLink );
    }
}
