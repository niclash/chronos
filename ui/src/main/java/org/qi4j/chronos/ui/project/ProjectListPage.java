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
package org.qi4j.chronos.ui.project;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.injection.scope.Uses;

@AuthorizeInstantiation( { SystemRole.ACCOUNT_ADMIN, SystemRole.ACCOUNT_DEVELOPER } )
public class ProjectListPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private IModel<HasProjects> hasProjects;

    public ProjectListPage( @Uses IModel<HasProjects> hasProjects )
    {
        this.hasProjects = hasProjects;

        initComponents();
    }

    private void initComponents()
    {
        Link newProjectLink = new Link( "newProjectLink" )
        {
            private static final long serialVersionUID = 1L;

            public void onClick()
            {
                PageParameters param = new PageParameters();
                String pageClassName = ProjectListPage.class.getName();
                param.put( pageClassName, ProjectListPage.this );

//                setResponsePage( newPage( ProjectAddPage.class, param ) );
            }
        };

        FeedbackPanel feedbackPanel = new FeedbackPanel( "feedbackPanel" );

        ProjectTable projectTable = new ProjectTable( "projectTable", hasProjects, new ProjectDataProvider( hasProjects ) );

        //authorise render/enable action
        MetaDataRoleAuthorizationStrategy.authorize( newProjectLink, RENDER, SystemRole.ACCOUNT_ADMIN );

        add( newProjectLink );
        add( feedbackPanel );
        add( projectTable );
    }

    protected int getSize()
    {
        return getAccount().projects().size();
    }
}
