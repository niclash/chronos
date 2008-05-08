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
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Money;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.CurrencyChoiceRenderer;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;
import org.qi4j.chronos.util.CurrencyUtil;
import org.qi4j.entity.association.Association;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class StaffAddEditPage extends AddEditBasePage
{
    private NumberTextField salaryAmountField;
    private DropDownChoice salaryCurrencyField;

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

        IChoiceRenderer renderer = new CurrencyChoiceRenderer();
        
        salaryAmountField = new NumberTextField( "salaryAmountField", "Salary" );
        salaryCurrencyField =
            new DropDownChoice( "salaryCurrencyChoice",
            new Model( (Serializable) CurrencyUtil.getCurrencyList() ),
            renderer );

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
        salaryAmountField.setModel( new CustomCompositeModel( salaryModel, "amount" ) );
    }

    protected void assignFieldValueToStaff( Staff staff )
    {
        userAddEditPanel.assignFieldValueToUser( staff );

        Currency currency = Currency.getInstance( salaryCurrencyField.getModelObjectAsString() );

        Association<Money> staffSalary = staff.salary();
        staffSalary.get().amount().set( salaryAmountField.getLongValue() );
        staffSalary.get().currency().set( currency );
    }

    protected void assignStaffToFieldValue( Staff staff )
    {
        userAddEditPanel.assignUserToFieldValue( staff );

        Money staffSalary = staff.salary().get();
        salaryAmountField.setLongValue( staffSalary.amount().get() );
//        salaryCurrencyField.setChoice( staffSalary.currency().get().getCurrencyCode() );
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
