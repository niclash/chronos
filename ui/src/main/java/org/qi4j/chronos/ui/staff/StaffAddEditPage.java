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
package org.qi4j.chronos.ui.staff;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.login.AbstractUserLoginPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.util.CurrencyUtil;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class StaffAddEditPage extends AddEditBasePage<Staff>
{
    private static final long serialVersionUID = 1L;

    private UserAddEditPanel<Staff> userAddEditPanel;

    public StaffAddEditPage( Page basePage, IModel<Staff> staff )
    {
        super( basePage, staff );
    }

    public void initComponent( Form<Staff> form )
    {
        userAddEditPanel = new UserAddEditPanel<Staff>( "userAddEditPanel", form.getModel() )
        {
            private static final long serialVersionUID = 1L;

            public AbstractUserLoginPanel getLoginUserAbstractPanel( String id )
            {
                return StaffAddEditPage.this.getLoginUserAbstractPanel( id );
            }

            public Iterator<SystemRole> getInitSelectedRoleList()
            {
                return StaffAddEditPage.this.getInitSelectedRoleList();
            }
        };

        TextField salaryAmountField = new TextField( "salary" );
        DropDownChoice salaryCurrencyField =
            new DropDownChoice( "salaryCurrencyChoice", CurrencyUtil.getCurrencyList() );

        form.add( salaryCurrencyField );
        form.add( salaryAmountField );
    }

    protected UserAddEditPanel<Staff> getUserAddEditPanel()
    {
        return this.userAddEditPanel;
    }

    public Iterator<SystemRole> getInitSelectedRoleList()
    {
        List<SystemRole> systemRoles = Collections.emptyList();
        return systemRoles.iterator();
    }

    public final void handleSubmitClicked( IModel<Staff> model )
    {
        onSubmitting( model );
    }

    protected abstract void onSubmitting( IModel<Staff> model );

    protected abstract AbstractUserLoginPanel getLoginUserAbstractPanel( String id );
}
