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
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.ui.common.NameChoiceRenderer;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.scope.Service;
import org.qi4j.entity.UnitOfWork;

public class LoginPage extends BasePage
{
    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_FEEDBACK_PANEL = "feedbackPanel";
    private static final String WICKET_ID_LOGIN_FORM = "loginForm";


    public LoginPage( @Service AccountService accountService )
        throws IllegalArgumentException
    {
        add( new FeedbackPanel( WICKET_ID_FEEDBACK_PANEL ) );

        add( new LoginForm( WICKET_ID_LOGIN_FORM, new LoginModel(), accountService ) );
    }

    private class LoginForm extends Form
    {
        private static final long serialVersionUID = 1L;

        private static final String WICKET_ID_ACCOUNT_DROP_DOWN_CHOICE = "accountDropDownChoice";
        private static final String WICKET_ID_USERNAME = "username";
        private static final String WICKET_ID_PASSWORD = "password";
        private DropDownChoice accountDropDownChoice;
        private static final String USER_NAME_BINDING = "userName";
        private static final String PASSWORD_BINDING = WICKET_ID_PASSWORD;
        private static final String SIGN_IN_FAILED = "signInFailed";

        private LoginForm( String aWicketId, LoginModel aLoginModel, AccountService accountService )
        {
            super( aWicketId );

            validateNotNull( "aLoginModel", aLoginModel );

            CompoundPropertyModel compoundPropertyModel = new CompoundPropertyModel( aLoginModel );
            setModel( compoundPropertyModel );

            getUnitOfWork();

            // Select account
            List<Account> availableAccounts = accountService.findAllAccounts();

            NameChoiceRenderer renderer = new NameChoiceRenderer();
            accountDropDownChoice =
                new DropDownChoice( WICKET_ID_ACCOUNT_DROP_DOWN_CHOICE, availableAccounts, renderer );
            add( accountDropDownChoice );
            accountDropDownChoice.setModel( new Model() );

            // username
            IModel userNameModel = compoundPropertyModel.bind( USER_NAME_BINDING );
            TextField username = new TextField( WICKET_ID_USERNAME, userNameModel );
            add( username );

            // password
            IModel passwordModel = compoundPropertyModel.bind( PASSWORD_BINDING );
            PasswordTextField password = new PasswordTextField( WICKET_ID_PASSWORD, passwordModel );
            add( password );
        }

        public final void onSubmit()
        {
            Account account = (Account) accountDropDownChoice.getModelObject();
            final LoginModel loginModel = (LoginModel) getModelObject();

            // Sign in user
            final ChronosSession session = ChronosSession.get();

            final UnitOfWork unitOfWork = getUnitOfWork();

            //if user logged in using account [System], the account wil be null.
            if( null != account )
            {
                account = unitOfWork.dereference( account );
            }

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
                error( getString( SIGN_IN_FAILED ) );
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
