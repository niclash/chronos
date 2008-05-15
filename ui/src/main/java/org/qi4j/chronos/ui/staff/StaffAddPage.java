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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.Money;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.MoneyEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserAddPanel;
import org.qi4j.chronos.util.CurrencyUtil;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.library.framework.validation.ValidationException;
import org.qi4j.library.general.model.GenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffAddPage extends StaffAddEditPage
{
    private static final Logger LOGGER = LoggerFactory.getLogger( StaffAddPage.class );
    private LoginUserAddPanel loginUserAddPanel;
    private static final String ADD_FAIL = "addFailed";
    private static final String ADD_SUCCESS = "addSuccessful";
    private static final String SUBMIT_BUTTON = "addPageSubmitButton";
    private static final String TITLE_LABEL = "addPageTitleLabel";

    public StaffAddPage( Page basePage )
    {
        super( basePage );

        validateNotNull( "basePage", basePage );

        bindModel();
    }

    private void bindModel()
    {
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        final UnitOfWork unitOfWork = getUnitOfWork();
                        final Staff staff =
                            unitOfWork.newEntityBuilder( StaffEntityComposite.class ).newInstance();
                        staff.gender().set( GenderType.MALE );
                        final Login staffLogin =
                            unitOfWork.newEntityBuilder( LoginEntityComposite.class ).newInstance();
                        staffLogin.isEnabled().set( true );
                        final Money staffSalary =
                            unitOfWork.newEntityBuilder( MoneyEntityComposite.class ).newInstance();
                        staffSalary.currency().set( CurrencyUtil.getDefaultCurrency() );
                        staff.login().set( staffLogin );
                        staff.salary().set( staffSalary );

                        return staff;
                    }
                }
            )
        );

        bindPropertyModel( getModel() );
    }

    public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
    {
        if( loginUserAddPanel == null )
        {
            loginUserAddPanel = new LoginUserAddPanel( id );
        }

        return loginUserAddPanel;
    }

    public String getSubmitButtonValue()
    {
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }

    public void onSubmitting()
    {
        final UnitOfWork unitOfWork = getUnitOfWork();
        try
        {
            final Staff staff = (Staff) getModelObject();
            final Account account = getAccount();
            for( SystemRole systemRole : getUserAddEditPanel().getSelectedRoleList() )
            {
                staff.systemRoles().add( systemRole );
            }
            account.staffs().add( staff );
            unitOfWork.complete();
            logInfoMsg( getString( ADD_SUCCESS ) );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            unitOfWork.reset();

            logErrorMsg( getString( ADD_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
        catch( ValidationException err )
        {
            unitOfWork.reset();

            logErrorMsg( getString( ADD_FAIL, new Model( err) ) );
            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }
}
