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

import java.util.Iterator;
import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserEditPanel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StaffEditPage extends StaffAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( StaffEditPage.class );

    private LoginUserEditPanel loginUserEditPanel;
    private static final String TITLE_LABEL = "editPageTitleLabel";
    private static final String SUBMIT_BUTTON = "editPageSubmitButton";
    private static final String UPDATE_SUCCESS = "updateSuccessful";
    private static final String UPDATE_FAIL = "updateFailed";

    public StaffEditPage( Page basePage, final String staffId )
    {
        super( basePage );

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( staffId, StaffEntityComposite.class );
                    }
                }
            )
        );
        bindPropertyModel( getModel() );
    }

    @Override public Iterator<SystemRole> getInitSelectedRoleList()
    {
        return getStaff().systemRoles().iterator();
    }

    public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
    {
        if( loginUserEditPanel == null )
        {
            loginUserEditPanel = new LoginUserEditPanel( id )
            {
                public User getUser()
                {
                    return StaffEditPage.this.getStaff();
                }
            };
        }

        return loginUserEditPanel;
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
        try
        {
            final Staff staff = (Staff) getModelObject();
            staff.systemRoles().clear();
            for( SystemRole systemRole : getUserAddEditPanel().getSelectedRoleList() )
            {
                staff.systemRoles().add( systemRole );
            }
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            logInfoMsg( getString( UPDATE_SUCCESS ) );

            divertToGoBackPage();
        }
        catch( Exception uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( getString( UPDATE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    public abstract Staff getStaff();
}
