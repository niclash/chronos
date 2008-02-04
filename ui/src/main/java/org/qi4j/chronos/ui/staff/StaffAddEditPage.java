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
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;
import org.qi4j.chronos.ui.util.ListUtil;
import org.qi4j.library.general.model.Money;
import org.qi4j.property.Property;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class StaffAddEditPage extends AddEditBasePage
{
    private NumberTextField salaryAmountField;
    private SimpleDropDownChoice<String> salaryCurrencyField;

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

            public Iterator<SystemRoleComposite> getInitSelectedRoleList()
            {
                return StaffAddEditPage.this.getInitSelectedRoleList();
            }
        };

        salaryAmountField = new NumberTextField( "salaryAmountField", "Salary" );
        salaryCurrencyField = new SimpleDropDownChoice<String>( "salaryCurrencyChoice", ListUtil.getCurrencyList(), true );

        form.add( salaryCurrencyField );
        form.add( salaryAmountField );
        form.add( userAddEditPanel );

        salaryAmountField.setIntValue( 0 );
    }

    protected void assignFieldValueToStaff( Staff staff )
    {
        userAddEditPanel.assignFieldValueToUser( staff );

        Currency currency = Currency.getInstance( salaryCurrencyField.getChoiceAsString() );

        Property<Money> staffSalary = staff.salary();
        staffSalary.get().amount().set( salaryAmountField.getLongValue() );
        staffSalary.get().currency().set( currency );
    }

    protected void assignStaffToFieldValue( Staff staff )
    {
        userAddEditPanel.assignUserToFieldValue( staff );

        Money staffSalary = staff.salary().get();
        salaryAmountField.setLongValue( staffSalary.amount().get() );
        salaryCurrencyField.setChoice( staffSalary.currency().get().getCurrencyCode() );
    }

    public Iterator<SystemRoleComposite> getInitSelectedRoleList()
    {
        List<SystemRoleComposite> systemRoles = Collections.emptyList();
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
