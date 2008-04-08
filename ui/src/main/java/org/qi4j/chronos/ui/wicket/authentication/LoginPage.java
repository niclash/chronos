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
package org.qi4j.chronos.ui.wicket.authentication;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.account.AccountDelegator;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.wicket.base.BasePage;

public class LoginPage extends BasePage
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_FEEDBACK_PANEL = "feedbackPanel";
    private static final String WICKET_ID_LOGIN_FORM = "loginForm";

    private PasswordTextField password;
    private TextField username;

    public LoginPage()
    {
        add( new FeedbackPanel( WICKET_ID_FEEDBACK_PANEL ) );
        add( new LoginForm( WICKET_ID_LOGIN_FORM ) );
    }

    public String getUsername()
    {
        return username.getModelObjectAsString();
    }

    public String getPassword()
    {
        return password.getModelObjectAsString();
    }

    private class LoginForm extends Form
    {
        private static final long serialVersionUID = 1L;

        private final static String SYSTEM_ACCOUNT = "[ Sytem ]";

        private final ValueMap properties = new ValueMap();
        private SimpleDropDownChoice<AccountDelegator> accountDropDownChoice;

        public LoginForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            List<AccountDelegator> accountList = getAvailableAccount();

            accountList.add( new AccountDelegator( SYSTEM_ACCOUNT, SYSTEM_ACCOUNT ) );

            accountDropDownChoice = new SimpleDropDownChoice<AccountDelegator>( "accountDropDownChoice", accountList, true );

            username = new TextField( "username", new PropertyModel( properties, "username" ) );
            password = new PasswordTextField( "password", new PropertyModel( properties, "password" ) )
            {
                 @Override
                 protected boolean supportsPersistence()
                {
                    return true;
                }
            };

            username.setPersistent( true );
            password.setPersistent( true );

            add( accountDropDownChoice );
            add( username );
            add( password );
        }

        public final void onSubmit()
        {
            String accountId = null;

            if( !accountDropDownChoice.getChoice().getId().equals( SYSTEM_ACCOUNT ) )
            {
                accountId = accountDropDownChoice.getChoice().getId();
            }

            if( signIn( accountId, getUsername(), getPassword() ) )
            {
                onSignInSucceeded();
            }
            else
            {
                onSignInFailed();
            }
        }

        private List<AccountDelegator> getAvailableAccount()
        {
            List<AccountDelegator> resultList = new ArrayList<AccountDelegator>();

            List<AccountEntityComposite> accounts = ChronosWebApp.getServices().getAccountService().findAll();

            for( AccountEntityComposite account : accounts )
            {
                resultList.add( new AccountDelegator( account ) );
            }

            return resultList;
        }

        private boolean signIn( String accountId, String username, String password )
        {
            ChronosSession session = ChronosSession.get();

            session.setAccountId( accountId );

            return session.signIn( username, password );
        }

        private void onSignInFailed()
        {
            error( getLocalizer().getString( "signInFailed", this, "Sign in failed" ) );
        }

        private void onSignInSucceeded()
        {
            if( !continueToOriginalDestination() )
            {
                setResponsePage( getApplication().getSessionSettings().getPageFactory().newPage(
                    getApplication().getHomePage(), (PageParameters) null ) );
            }
        }
    }
}