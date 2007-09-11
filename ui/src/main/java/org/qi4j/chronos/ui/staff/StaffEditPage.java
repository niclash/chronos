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

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserEditPanel;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.library.general.model.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StaffEditPage extends StaffAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( StaffEditPage.class );

    private LoginUserEditPanel loginUserEditPanel;

    public StaffEditPage( BasePage basePage, String accountId )
    {
        super( basePage, accountId );

        initData();
    }

    private void initData()
    {
        Staff staff = getStaff();

        userAddEditPanel.getFirstNameField().setText( staff.getFirstName() );
        userAddEditPanel.getLastNameField().setText( staff.getLastName() );
        userAddEditPanel.getGenderChoice().setChoice( staff.getGender().toString() );

        Money salary = staff.getSalary();

        if( salary != null )
        {
            salaryAmountField.setLongValue( salary.getAmount() );
            salaryCurrencyField.setChoice( salary.getCurrency().getCurrencyCode() );
        }
        else
        {
            salaryAmountField.setIntValue( 0 );
        }

        loginUserEditPanel.getLoginEnabledCheckBox().setModel( new Model( staff.getLogin().isEnabled() ) );
    }

    public List<SystemRoleEntityComposite> getInitSelectedRoleList()
    {
        Staff staff = getStaff();

        Iterator<SystemRoleEntityComposite> roleIterator = staff.systemRoleIterator();

        List<SystemRoleEntityComposite> returnList = new ArrayList<SystemRoleEntityComposite>();

        while( roleIterator.hasNext() )
        {
            returnList.add( roleIterator.next() );
        }

        return returnList;
    }

    private StaffEntityComposite getStaff()
    {
        return getStaffService().get( getStaffId() );
    }

    public StaffService getStaffService()
    {
        return ChronosWebApp.getServices().getStaffService();
    }

    public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
    {
        if( loginUserEditPanel == null )
        {
            loginUserEditPanel = new LoginUserEditPanel( id, getStaffId() );
        }

        return loginUserEditPanel;
    }

    public String getSubmitButtonValue()
    {
        return "Edit";
    }

    public String getTitleLabel()
    {
        return "Edit staff";
    }

    public void onSubmitting()
    {
        StaffEntityComposite staff = getStaff();

        staff.setFirstName( userAddEditPanel.getFirstNameField().getText() );
        staff.setLastName( userAddEditPanel.getLastNameField().getText() );

        GenderType genderType = GenderType.getGenderType( userAddEditPanel.getGenderChoice().getChoiceAsString() );
        staff.setGender( genderType );

        boolean isLoginEnabled = Boolean.valueOf( loginUserEditPanel.getLoginEnabledCheckBox().getModelObjectAsString() );

        staff.getLogin().setEnabled( isLoginEnabled );

        Currency currency = Currency.getInstance( salaryCurrencyField.getChoiceAsString() );

        staff.getSalary().setAmount( salaryAmountField.getLongValue() );
        staff.getSalary().setCurrency( currency );

        staff.removeAllSystemRole();

        List<SystemRoleEntityComposite> roleLists = userAddEditPanel.getSelectedRoleList();

        for( SystemRoleEntityComposite role : roleLists )
        {
            staff.addSystemRole( role );
        }

        try
        {
            getStaffService().update( staff );

            logInfoMsg( "Staff is updated successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage() );
        }
    }

    public abstract String getStaffId();

}
