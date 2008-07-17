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

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;

public abstract class ProjectRoleDetailPage extends LeftMenuNavPage
{
    private Page goBackPage;

    public ProjectRoleDetailPage( Page goBackPage )
    {
        this.goBackPage = goBackPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new RoleDetailForm( "roleDetailForm" ) );
    }

    private class RoleDetailForm extends Form
    {
        private Button returnButton;
        private SimpleTextField roleNameField;

        public RoleDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            ProjectRole projectRole = getProjectRole();

            roleNameField = new SimpleTextField( "roleName", projectRole.name().get(), false );

            returnButton = new Button( "submitButton", new Model( "Return" ) );

            add( roleNameField );
            add( returnButton );
        }

        protected void delegateSubmit( IFormSubmittingComponent iFormSubmittingComponent )
        {
            if( iFormSubmittingComponent == returnButton )
            {
                handleReturn();
            }
        }

        private void handleReturn()
        {
            setResponsePage( goBackPage );
        }
    }

    public abstract ProjectRole getProjectRole();
}
