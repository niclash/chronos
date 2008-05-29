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
package org.qi4j.chronos.ui.relationship;

import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class RelationshipAddEditPage extends AddEditBasePage<Relationship>
{
    private static final long serialVersionUID = 1L;

    public RelationshipAddEditPage( Page goBackPage, IModel<Relationship> model )
    {
        super( goBackPage, model );
    }

    public void initComponent( Form<Relationship> form )
    {
        TextField relationshipField = new TextField( "relationship" );
        form.add( relationshipField );
    }

    public void handleSubmitClicked( IModel<Relationship> model )
    {
        onSubmitting( model );
    }

    public abstract void onSubmitting( IModel<Relationship> model );
}
