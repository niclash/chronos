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

import java.util.Iterator;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserEditPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ContactPersonEditPage extends ContactPersonAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ContactPersonEditPage.class );

    private LoginUserEditPanel loginUserEditPanel;

    public ContactPersonEditPage( BasePage basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        ContactPersonEntityComposite contactPerson = getContactPerson();

        assignContactPersonToFieldValue( contactPerson );
    }

    public Iterator<SystemRoleEntityComposite> getInitSelectedRoleList()
    {
        return getContactPerson().systemRoleIterator();
    }

    public void onSubmitting()
    {
        ContactPersonEntityComposite contactPerson = getContactPerson();

        //bp. ContactPerson has and only has one system role which is SystemRole.CONTACT_PERSON
        //hence, we don't need to remove all system role.
        assignFieldValueToContactPerson( contactPerson );

        try
        {
            ChronosWebApp.getServices().getContactPersonService().update( contactPerson );

            logInfoMsg( "Contact Person is updated successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage() );
        }
    }

    public LoginUserAbstractPanel getLoginUserAbstractPanel( String id )
    {
        if( loginUserEditPanel == null )
        {
            loginUserEditPanel = new LoginUserEditPanel( id )
            {
                public User getUser()
                {
                    return ContactPersonEditPage.this.getContactPerson();
                }
            };
        }

        return loginUserEditPanel;
    }

    public String getSubmitButtonValue()
    {
        return "Edit";
    }

    public String getTitleLabel()
    {
        return "Edit Contact Person";
    }

    public Iterator<ContactComposite> getInitContactIterator()
    {
        return ChronosWebApp.getServices().getContactService().findAll( getContactPerson() ).iterator();
    }

    public abstract ContactPersonEntityComposite getContactPerson();
}
