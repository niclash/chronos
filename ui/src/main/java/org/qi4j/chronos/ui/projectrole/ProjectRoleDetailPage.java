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
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public final class ProjectRoleDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private Page goBackPage;

    public ProjectRoleDetailPage( Page goBackPage, IModel<ProjectRole> projectRoleModel )
    {
        this.goBackPage = goBackPage;

        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new RoleDetailForm( "roleDetailForm", projectRoleModel ) );
    }

    private class RoleDetailForm extends Form<ProjectRole>
    {
        private static final long serialVersionUID = 1L;

        private Button returnButton;

        public RoleDetailForm( String id, IModel<ProjectRole> projectRoleModel )
        {
            super( id );

            ChronosCompoundPropertyModel<ProjectRole> model = new ChronosCompoundPropertyModel<ProjectRole>( projectRoleModel.getObject() );

            setModel( model );

            TextField roleNameField = new TextField<String>( "roleName", model.<String>bind( "name" ) );

            returnButton = new Button( "submitButton", new Model<String>( "Return" ) );

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
}
