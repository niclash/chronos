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

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.login.AbstractUserLoginPanel;
import org.qi4j.chronos.ui.login.UserLoginAddPanel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffAddPage extends StaffAddEditPage
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger( StaffAddPage.class );
    private UserLoginAddPanel userLoginAddPanel;

    public StaffAddPage( Page basePage, IModel<Staff> staff )
    {
        super( basePage, staff );
    }

    public void onSubmitting()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

//    private void bindModel()
//    {
//        setModel(
//            new CompoundPropertyModel(
//                new LoadableDetachableModel()
//                {
//                    public Object load()
//                    {
//                        final Staff staff =
//                            ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().newEntityBuilder( StaffEntityComposite.class ).newInstance();
//                        staff.gender().set( GenderType.MALE );
//                        final Login staffLogin =
//                            ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().newEntityBuilder( LoginEntityComposite.class ).newInstance();
//                        staffLogin.isEnabled().set( true );
//                        final Money staffSalary =
//                            ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().newEntityBuilder( MoneyEntityComposite.class ).newInstance();
//                        staffSalary.currency().set( CurrencyUtil.getDefaultCurrency() );
//                        staff.login().set( staffLogin );
//                        staff.salary().set( staffSalary );
//
//                        return staff;
//                    }
//                }
//            )
//        );
//
//        bindPropertyModel( getModel() );
//    }

    public AbstractUserLoginPanel getLoginUserAbstractPanel( String id )
    {
        if( userLoginAddPanel == null )
        {
            userLoginAddPanel = new UserLoginAddPanel( id );
        }

        return userLoginAddPanel;
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "New Staff";
    }

    public void onSubmitting( IModel<Staff> model )
    {
        try
        {
            Staff staff = model.getObject();
            Account account = getAccount();

            for( SystemRole systemRole : getUserAddEditPanel().getSelectedRoleList() )
            {
                staff.systemRoles().add( systemRole );
            }
            account.staffs().add( staff );
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            logInfoMsg( "Staff was added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( "Fail to add new staff." );
            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }
}
