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

import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.login.LoginUserAbstractPanel;
import org.qi4j.chronos.ui.login.LoginUserAddPanel;

public class ContactPersonAddPage extends ContactPersonAddEditPage
{
    private LoginUserAddPanel loginUserAddPanel;

    public ContactPersonAddPage( BasePage basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
        //TODO
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
        return "Add";
    }

    public String getTitleLabel()
    {
        return "New Contact Person";
    }
}
