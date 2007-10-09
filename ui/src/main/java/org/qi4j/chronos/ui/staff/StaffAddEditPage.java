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
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.NumberTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;
import org.qi4j.chronos.ui.util.ListUtil;

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

            public Iterator<SystemRoleEntityComposite> getInitSelectedRoleList()
            {
                return StaffAddEditPage.this.getInitSelectedRoleList();
            }
        };

        salaryAmountField = new NumberTextField( "salaryAmountField", "Salary" );
        salaryCurrencyField = new SimpleDropDownChoice( "salaryCurrencyChoice", ListUtil.getCurrencyList(), true );

        form.add( salaryCurrencyField );
        form.add( salaryAmountField );
        form.add( userAddEditPanel );

        salaryAmountField.setIntValue( 0 );
    }

    protected void assignFieldValueToStaff( Staff staff )
    {
        userAddEditPanel.assignFieldValueToUser( staff );

        Currency currency = Currency.getInstance( salaryCurrencyField.getChoiceAsString() );

        staff.getSalary().setAmount( salaryAmountField.getLongValue() );
        staff.getSalary().setCurrency( currency );
    }

    protected void assignStaffToFieldValue( Staff staff )
    {
        userAddEditPanel.assignUserToFieldValue( staff );

        salaryAmountField.setLongValue( staff.getSalary().getAmount() );
        salaryCurrencyField.setChoice( staff.getSalary().getCurrency().getCurrencyCode() );
    }

    public Iterator<SystemRoleEntityComposite> getInitSelectedRoleList()
    {
        return Collections.EMPTY_LIST.iterator();
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
