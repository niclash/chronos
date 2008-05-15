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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.common.model.GenericCustomCompositeModel;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.util.CurrencyUtil;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class StaffAddEditPage extends AddEditBasePage
{
    private NumberTextField salaryAmountField;
    private SimpleDropDownChoice salaryCurrencyField;
    private UserAddEditPanel userAddEditPanel;

    public StaffAddEditPage( Page basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        userAddEditPanel = new UserAddEditPanel( "userAddEditPanel" )
        {
            public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
            {
                return StaffAddEditPage.this.getLoginUserAbstractPanel( id );
            }

            public Iterator<SystemRole> getInitSelectedRoleList()
            {
                return StaffAddEditPage.this.getInitSelectedRoleList();
            }
        };

        salaryAmountField = new NumberTextField( "salaryAmountField", "Salary" );
        salaryCurrencyField =
            new SimpleDropDownChoice( "salaryCurrencyChoice",
            CurrencyUtil.getCurrencyList(), true );

        form.add( salaryCurrencyField );
        form.add( salaryAmountField );
        form.add( userAddEditPanel );

        salaryAmountField.setIntValue( 0 );
    }

    protected void bindPropertyModel( IModel iModel )
    {
        userAddEditPanel.bindPropertyModel( iModel );
        IModel salaryModel = new CustomCompositeModel( iModel, "salary" );
        salaryCurrencyField.setModel( new CustomCompositeModel( salaryModel, "currency" ) );
        salaryAmountField.setModel( new GenericCustomCompositeModel<Long>( salaryModel, "amount" ) );
    }

    protected UserAddEditPanel getUserAddEditPanel()
    {
        return this.userAddEditPanel;
    }

    public Iterator<SystemRole> getInitSelectedRoleList()
    {
        List<SystemRole> systemRoles = Collections.emptyList();
        return systemRoles.iterator();
    }

    public final void handleSubmit()
    {
        boolean isRejected = false;

        if( salaryAmountField.checkIsEmptyOrNotLong() )
        {
            isRejected = true;
        }

        if( userAddEditPanel.checkIsNotValidated() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public abstract void onSubmitting();

    public abstract LoginUserAbstractPanel getLoginUserAbstractPanel( String id );
}
