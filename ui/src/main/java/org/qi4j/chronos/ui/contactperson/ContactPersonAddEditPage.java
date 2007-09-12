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
package org.qi4j.chronos.ui.contactperson;

import java.util.Collections;
import java.util.Iterator;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.user.UserAddEditPanel;

public abstract class ContactPersonAddEditPage extends AddEditBasePage
{
    private UserAddEditPanel userAddEditPanel;

    private String projectOwnerId;

    public ContactPersonAddEditPage( BasePage goBackPage, String projectOwnerId )
    {
        super( goBackPage );

        this.projectOwnerId = projectOwnerId;
    }

    protected ProjectOwnerEntityComposite getProjectOwner()
    {
        return ChronosWebApp.getServices().getProjectOwnerService().get( projectOwnerId );
    }

    public void initComponent( Form form )
    {
        userAddEditPanel = new UserAddEditPanel( "userAddEditPanel", true )
        {
            public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
            {
                return ContactPersonAddEditPage.this.getLoginUserAbstractPanel( id );
            }

            public Iterator<SystemRoleEntityComposite> getInitSelectedRoleList()
            {
                return ContactPersonAddEditPage.this.getInitSelectedRoleList();
            }
        };

        form.add( userAddEditPanel );
    }

    protected void assignContactPersonToFieldValue( ContactPerson contactPerson )
    {
        userAddEditPanel.assignUserToFieldValue( contactPerson );
    }

    protected void assignFieldValueToContactPerson( ContactPerson contactPerson )
    {
        userAddEditPanel.assignFieldValueToUser( contactPerson );
    }

    public Iterator<SystemRoleEntityComposite> getInitSelectedRoleList()
    {
        return Collections.EMPTY_LIST.iterator();
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

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
