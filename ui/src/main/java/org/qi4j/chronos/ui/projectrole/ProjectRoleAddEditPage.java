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
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class ProjectRoleAddEditPage extends AddEditBasePage<ProjectRole>
{
    private static final long serialVersionUID = 1L;

    public ProjectRoleAddEditPage( Page goBackPage, IModel<ProjectRole> model )
    {
        super( goBackPage, model );
    }

    public final void initComponent( Form<ProjectRole> form )
    {
        TextField nameField = new TextField( "name" );

        form.add( nameField );
    }

    public final void handleSubmitClicked( IModel<ProjectRole> model )
    {
        onSubmitting( model );
    }

    public abstract void onSubmitting( IModel<ProjectRole> model );
}
