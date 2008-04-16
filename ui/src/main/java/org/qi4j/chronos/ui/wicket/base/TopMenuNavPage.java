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
package org.qi4j.chronos.ui.wicket.base;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;

public abstract class TopMenuNavPage extends BasePage
{
    public TopMenuNavPage()
    {
        initComponents();
    }

    private void initComponents()
    {
        add( new Label( "accountName", getAccountName() ) );

        add( new Label( "loginId", getUser().login().get().name().get() ) );

        add( new Link( "changePasswordLink" )
        {
            public void onClick()
            {
                handleChangePassword();
            }
        } );

        add( new Link( "logoutLink" )
        {
            public void onClick()
            {
                // invalidate the session.
                getSession().invalidate();

                setResponsePage( LoginPage.class );
            }
        } );
    }

    private String getAccountName()
    {
        ChronosSession chronosSession = ChronosSession.get();

        Account account = chronosSession.getAccount();

        return account == null ? null : account.name().get();
    }

    private void handleChangePassword()
    {
        setResponsePage( new ChangePasswordPage( this )
        {
            public User getUser()
            {
                return TopMenuNavPage.this.getUser();
            }
        } );
    }

    private User getUser()
    {
        ChronosSession session = ChronosSession.get();

        return session.getUser();
    }

    public Services getServices()
    {
        return ChronosWebApp.getServices();
    }
}
