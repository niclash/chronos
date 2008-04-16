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
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.account.AccountDelegator;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.service.account.AccountServiceComposite;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Service;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;

import static org.qi4j.composite.NullArgumentException.validateNotNull;

public class LoginPage extends BasePage
{
    transient private UnitOfWorkFactory factory;

//    transient private AccountEntityServiceComposite accountService;
//    transient private YetAnotherAccountServiceComposite accountService;
    transient private AccountServiceComposite accountService;

    private static final long serialVersionUID = 1L;

    private static final String WICKET_ID_FEEDBACK_PANEL = "feedbackPanel";
    private static final String WICKET_ID_LOGIN_FORM = "loginForm";

    private PasswordTextField password;
    private TextField username;

    public LoginPage( final @Structure UnitOfWorkFactory factory, final @Service AccountServiceComposite accountService )
    {
        validateNotNull( "factory", factory );
        validateNotNull( "accountService", accountService );

        this.factory = factory;
        this.accountService = accountService;
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

        private final static String SYSTEM_ACCOUNT = "[ System ]";

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

            username = new TextField( "username", new PropertyModel( properties, "username" ) )
            {
                @Override
                protected boolean supportsPersistence()
                {
                    return true;
                }
            };
            password = new PasswordTextField( "password", new PropertyModel( properties, "password" ) )
            {
                 @Override
                 protected boolean supportsPersistence()
                {
                    return true;
                }
            };

//            username.setPersistent( true );
//            password.setPersistent( true );

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

//            UnitOfWork uow = factory.newUnitOfWork();

//            List<Account> accounts = ChronosSession.get().getAccountService().findAll( uow );
            List<Account> accounts = accountService.findAll();

            for( Account account : accounts )
            {
                resultList.add( new AccountDelegator( account ) );
            }
//            uow.discard();

            return resultList;
        }

        private boolean signIn( String accountId, String username, String password )
        {
            ChronosSession session = ChronosSession.get();
            session.setAccountId( accountId );

//            UnitOfWork uow = factory.newUnitOfWork();
//            for( Login login : session.getLoginService().findAll( uow ) )
//            {
//                if( login.isEnabled().get() && username.equals( login.name().get() ) )
//                {
//                    return password.equals( login.password().get() );
//                }
//            }
//            uow.discard();
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