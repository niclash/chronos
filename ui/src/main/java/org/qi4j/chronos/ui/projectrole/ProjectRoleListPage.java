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
package org.qi4j.chronos.ui.projectrole;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public class ProjectRoleListPage extends LeftMenuNavPage
{
    public ProjectRoleListPage()
    {
        initComponents();
    }

    private void initComponents()
    {
        add( new Link( "newRoleLink" )
        {
            public void onClick()
            {
                setResponsePage( new ProjectRoleAddPage( ProjectRoleListPage.this ) );
            }
        } );

        add( new FeedbackPanel( "feedbackPanel" ) );

        ProjectRoleTable roleTable = new ProjectRoleTable( "roleTable" )
        {
            public AccountEntityComposite getAccount()
            {
                return ProjectRoleListPage.this.getAccount();
            }
        };

        add( roleTable );
    }
    
}
