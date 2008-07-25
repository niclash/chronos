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

import org.apache.wicket.IPageMap;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;

public abstract class TopMenuNavPage extends BasePage
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_ACCOUNT_NAME = "accountName";
    private static final String WICKET_ID_LOGIN_ID = "loginId";
    private static final String WICKET_ID_CHANGE_PASSWORD_LINK = "changePasswordLink";
    private static final String WICKET_ID_LOGOUT_LINK = "logoutLink";

    public TopMenuNavPage()
    {
        add( new Label( WICKET_ID_ACCOUNT_NAME, getAccountName() ) );
        add( new Label( WICKET_ID_LOGIN_ID, getUserLogin() ) );
        add( new ChangePasswordLink() );
        add( new LogoutLink() );
    }

    private String getUserLogin()
    {
        User user = getChronosSession().getUser();
        return user.login().get().name().get();
    }

    private String getAccountName()
    {
        Account account = getChronosSession().getAccount();
        if( account == null )
        {
            return null;
        }
        else
        {
            return account.name().get();
        }
    }

    private static class ChangePasswordLink extends Link
    {
        private static final long serialVersionUID = 1L;

        private ChangePasswordLink()
        {
            super( WICKET_ID_CHANGE_PASSWORD_LINK );
        }

        @Override
        public final void onClick()
        {
            Page page = getPage();
            setResponsePage( new ChangePasswordPage( page ) );
        }
    }

    private static class LogoutLink extends Link
    {
        private static final long serialVersionUID = 1L;

        private LogoutLink()
        {
            super( WICKET_ID_LOGOUT_LINK );
        }

        @Override
        public final void onClick()
        {
            // invalidate the session.
            getSession().invalidate();

            setResponsePage( LoginPage.class );
        }
    }
}
