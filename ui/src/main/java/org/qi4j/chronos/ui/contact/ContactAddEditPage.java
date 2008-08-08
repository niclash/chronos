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
package org.qi4j.chronos.ui.contact;

import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.library.general.model.Contact;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class ContactAddEditPage extends AddEditBasePage<Contact>
{
    private static final long serialVersionUID = 1L;

    public ContactAddEditPage( Page basePage, IModel<Contact> contact )
    {
        super( basePage, contact );
    }

    public void initComponent( Form<Contact> form )
    {
        ChronosCompoundPropertyModel<Contact> model = (ChronosCompoundPropertyModel<Contact>) form.getModel();

        RequiredTextField<String> valueField = new RequiredTextField<String>( "contactValue", model.<String>bind( "contactValue" ) );
        RequiredTextField<String> contactTypeField = new RequiredTextField<String>( "contactType", model.<String>bind( "contactType" ) );

        form.add( valueField );
        form.add( contactTypeField );
    }

    public void handleSubmitClicked( IModel<Contact> model )
    {
        onSubmitting( model );
    }

    public abstract void onSubmitting( IModel<Contact> model );
}
