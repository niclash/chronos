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
package org.qi4j.chronos.ui.projectowner;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.qi4j.chronos.ui.ChronosSession;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;

public class ProjectOwnerListPage extends LeftMenuNavPage
{
    private String accountId;

    public ProjectOwnerListPage()
    {
        this( ( (ChronosSession) ChronosSession.get() ).getAccountId() );
    }

    public ProjectOwnerListPage( String accountId )
    {
        this.accountId = accountId;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );

        add( new Link( "newProjectOwnerLink" )
        {
            public void onClick()
            {
                setResponsePage( new ProjectOwnerAddPage( ProjectOwnerListPage.this, accountId ) );
            }
        } );

        ProjectOwnerTable projectOwnerTable = new ProjectOwnerTable( "projectOwnerTable" )
        {
            public String getAccountId()
            {
                return accountId;
            }
        };

        add( projectOwnerTable );
    }
}
