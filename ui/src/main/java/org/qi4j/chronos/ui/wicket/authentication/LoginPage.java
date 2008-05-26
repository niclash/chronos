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
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.composite.scope.Service;
import org.qi4j.entity.Identity;

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

    private class LoginForm extends Form<LoginModel>
    {
        private static final long serialVersionUID = 1L;

        private static final String WICKET_ID_ACCOUNT_DROP_DOWN_CHOICE = "accountDropDownChoice";

        private static final String WICKET_ID_USERNAME = "username";
        private static final String WICKET_ID_PASSWORD = "password";

        private DropDownChoice<ChronosDetachableModel<Account>> accountDropDownChoice;

        private LoginForm( String aWicketId, LoginModel aLoginModel, AccountService accountService )
        {
            super( aWicketId );

            validateNotNull( "aLoginModel", aLoginModel );

            CompoundPropertyModel<LoginModel> compoundPropertyModel = new CompoundPropertyModel<LoginModel>( aLoginModel );
            setModel( compoundPropertyModel );

            List<Account> availableAccounts = accountService.findAllAccounts();
            List<ChronosDetachableModel<Account>> accountModels = new ArrayList<ChronosDetachableModel<Account>>();

            for( Account account : availableAccounts )
            {
                accountModels.add( new ChronosDetachableModel<Account>( account ) );
            }

            //account
            accountDropDownChoice = new DropDownChoice<ChronosDetachableModel<Account>>(
                WICKET_ID_ACCOUNT_DROP_DOWN_CHOICE, accountModels, new AccountDropDownChoiceRenderer() );

            IModel<ChronosDetachableModel<Account>> accountModel = compoundPropertyModel.bind( "account" );
            accountDropDownChoice.setModel( accountModel );

            add( accountDropDownChoice );

            // username
            IModel<String> userNameModel = compoundPropertyModel.bind( "userName" );
            TextField<String> username = new TextField<String>( WICKET_ID_USERNAME, userNameModel );
            add( username );

            // password
            IModel<String> passwordModel = compoundPropertyModel.bind( "password" );
            PasswordTextField password = new PasswordTextField( WICKET_ID_PASSWORD, passwordModel );
            add( password );
        }

        public final void onSubmit()
        {
            ChronosDetachableModel<Account> accountDetachableModel = accountDropDownChoice.getModelObject();

            final LoginModel loginModel = getModelObject();

            final ChronosSession session = ChronosSession.get();

            session.setAccount( accountDetachableModel.getObject() );

            String userName = loginModel.getUserName();
            String password = loginModel.getPassword();
            boolean isSignedIn = session.signIn( userName, password );

            if( isSignedIn )
            {
                if( !continueToOriginalDestination() )
                {
                    Application application = getApplication();
                    setResponsePage( application.getHomePage() );
                }
            }
            else
            {
                error( "Invalid username or password" );
            }
        }
    }

    private static final class AccountDropDownChoiceRenderer implements IChoiceRenderer<ChronosDetachableModel<Account>>
    {
        private static final long serialVersionUID = 1L;

        public Object getDisplayValue( ChronosDetachableModel<Account> object )
        {
            return object.getObject().name().get();
        }

        public String getIdValue( ChronosDetachableModel<Account> object, int index )
        {
            return ( (Identity) object.getObject() ).identity().get();
        }
    }

    private static final class LoginModel
        implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private String userName;
        private String password;

        private ChronosDetachableModel<Account> account;

        public ChronosDetachableModel<Account> getAccount()
        {
            return account;
        }

        public void setAccount( ChronosDetachableModel<Account> account )
        {
            this.account = account;
        }

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
