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

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.associations.HasLogin;
import org.qi4j.chronos.ui.common.MaxLengthPasswordField;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ChangePasswordPage extends LeftMenuNavPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ChangePasswordPage.class );

    private Page goBackPage;
    private static final String UPDATE_SUCCESS = "updateSuccessful";
    private static final String UPDATE_FAIL = "updateFailed";
    private static final String INVALID_PASSWORD = "invalidPassword";
    private static final String MISMATCH_PASSWORD = "mismatchPassword";

    public ChangePasswordPage( Page goBackPage )
    {
        this.goBackPage = goBackPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ChangePasswordForm( "changePasswordForm" ) );
    }

    public abstract HasLogin getHasLogin();

    private class ChangePasswordForm extends Form
    {
        private MaxLengthPasswordField oldPasswordField;
        private MaxLengthPasswordField newPasswordField;
        private MaxLengthPasswordField confirmPasswordField;

        private Button submitButton;
        private Button cancelButton;

        private Label loginIdLabel;

        public ChangePasswordForm( String id )
        {
            super( id );

            oldPasswordField = new MaxLengthPasswordField( "oldPassword", "Old Password", Login.PASSWORD_LEN );
            newPasswordField = new MaxLengthPasswordField( "newPassword", "New Password", Login.PASSWORD_LEN );
            confirmPasswordField = new MaxLengthPasswordField( "confirmPassword", "Confirm Password", Login.PASSWORD_LEN );

            loginIdLabel = new Label( "loginId", getHasLogin().login().get().name().get() );
            submitButton = new Button( "submitButton", new Model( "Change Password" ) );
            cancelButton = new Button( "cancelButton", new Model( "Cancel" ) );

            oldPasswordField.setRequired( false );
            newPasswordField.setRequired( false );
            confirmPasswordField.setRequired( false );

            add( oldPasswordField );
            add( newPasswordField );
            add( confirmPasswordField );
            add( loginIdLabel );

            add( submitButton );
            add( cancelButton );
        }

        protected void delegateSubmit( IFormSubmittingComponent iFormSubmittingComponent )
        {
            if( iFormSubmittingComponent == submitButton )
            {
                handleChangePassword();
            }
            else if( iFormSubmittingComponent == cancelButton )
            {
                setResponsePage( goBackPage );
            }
            else
            {
                throw new IllegalArgumentException( "" );
            }
        }

        private void handleChangePassword()
        {

            boolean isRejected = false;

            if( oldPasswordField.checkIsEmptyOrInvalidLength() )
            {
                isRejected = true;
            }

            if( newPasswordField.checkIsEmptyOrInvalidLength() )
            {
                isRejected = true;
            }

            if( confirmPasswordField.checkIsEmptyOrInvalidLength() )
            {
                isRejected = true;
            }

            String oldPassword = oldPasswordField.getText();
            String password = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if( password != null && confirmPassword != null )
            {
                if( !password.equals( confirmPassword ) )
                {
                    error( getString( MISMATCH_PASSWORD ) );
                    isRejected = true;
                }
            }

            Login userLogin = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( getHasLogin().login().get() );
            if( oldPassword != null && !userLogin.password().get().equals( oldPassword ) )
            {
                error( getString( INVALID_PASSWORD ) );
                isRejected = true;
            }

            if( isRejected )
            {
                return;
            }

            userLogin.password().set( password );

            try
            {
                ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

                goBackPage.info( getString( UPDATE_SUCCESS ) );

                setResponsePage( goBackPage );
            }
            catch( Exception err )
            {
                ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();
                error( getString( UPDATE_FAIL, new Model( err ) ) );
                LOGGER.error( err.getLocalizedMessage(), err );
            }
        }
    }
}
