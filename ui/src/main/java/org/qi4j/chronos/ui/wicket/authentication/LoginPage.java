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

import java.io.Serializable;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.Localizer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import static org.qi4j.composite.NullArgumentException.*;
import org.qi4j.composite.scope.Service;
import org.qi4j.entity.Identity;

public class LoginPage extends BasePage
{

    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_FEEDBACK_PANEL = "feedbackPanel";
    private static final String WICKET_ID_LOGIN_FORM = "loginForm";

    public LoginPage( @Service AccountService anAccountService )
        throws IllegalArgumentException
    {
        validateNotNull( "anAccountService", anAccountService );

        add( new FeedbackPanel( WICKET_ID_FEEDBACK_PANEL ) );
        add( new LoginForm( WICKET_ID_LOGIN_FORM, new LoginModel(), anAccountService ) );
    }

    private static class LoginForm extends Form
    {
        private static final long serialVersionUID = 1L;

        private static final String WICKET_ID_ACCOUNT_DROP_DOWN_CHOICE = "accountDropDownChoice";
        private static final String WICKET_ID_USERNAME = "username";
        private static final String WICKET_ID_PASSWORD = "password";

        private DropDownChoice accountDropDownChoice;

        private LoginForm( String aWicketId, LoginModel aLoginModel, AccountService anAccountService )
        {
            super( aWicketId );

            validateNotNull( "aLoginModel", aLoginModel );
            validateNotNull( "anAccountService", anAccountService );

            CompoundPropertyModel compoundPropertyModel = new CompoundPropertyModel( aLoginModel );
            setModel( compoundPropertyModel );

            // Select account
            List<Account> availableAccounts = anAccountService.findAvailableAccounts();
            AccountChoiceRenderer renderer = new AccountChoiceRenderer();
            accountDropDownChoice = new DropDownChoice( WICKET_ID_ACCOUNT_DROP_DOWN_CHOICE, availableAccounts, renderer );
            add( accountDropDownChoice );
            accountDropDownChoice.setModel( new Model() );

            // username
            IModel userNameModel = compoundPropertyModel.bind( "userName" );
            TextField username = new TextField( WICKET_ID_USERNAME, userNameModel );
            add( username );

            // password
            IModel passwordModel = compoundPropertyModel.bind( "password" );
            PasswordTextField password = new PasswordTextField( WICKET_ID_PASSWORD, passwordModel );
            add( password );
        }

        public final void onSubmit()
        {
            Account account = (Account) accountDropDownChoice.getModelObject();
            LoginModel loginModel = (LoginModel) getModelObject();

            // Sign in user
            ChronosSession session = ChronosSession.get();
            session.setAccount( account );
            String userName = loginModel.getUserName();
            String password = loginModel.getPassword();
            boolean isSignedIn = session.signIn( userName, password );

            if( isSignedIn )
            {
                if( !continueToOriginalDestination() )
                {
                    Application application = getApplication();
                    Class homePage = application.getHomePage();
                    setResponsePage( newPage( homePage, null ) );
                }
            }
            else
            {
                Localizer localizer = getLocalizer();
                error( localizer.getString( "signInFailed", this, "Sign in failed" ) );
            }
        }

        /**
         * {@code AccountChoiceRenderer} is used to render account in drop down choice.
         *
         * @author edward.yakop@gmail.com
         */
        private static class AccountChoiceRenderer
            implements IChoiceRenderer
        {
            private static final long serialVersionUID = 1L;

            public final Object getDisplayValue( Object anObjAccount )
            {
                Account account = (Account) anObjAccount;
                return account.name().get();
            }

            public final String getIdValue( Object objIdentity, int index )
            {
                Identity identity = (Identity) objIdentity;
                return identity.identity().get();
            }
        }
    }

    private static final class LoginModel
        implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private String userName;
        private String password;

        public final String getPassword()
        {
            return password;
        }

        public final void setPassword( String aPassword )
        {
            password = aPassword;
        }

        public final String getUserName()
        {
            return userName;
        }

        public final void setUserName( String aUserName )
        {
            userName = aUserName;
        }
    }
}