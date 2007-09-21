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
package org.qi4j.chronos.ui.base;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.service.UserService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.MaxLengthPasswordField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangePasswordPage extends LeftMenuNavPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ChangePasswordPage.class );

    private BasePage goBackPage;
    private String userId;

    public ChangePasswordPage( BasePage goBackPage, String userId )
    {
        this.goBackPage = goBackPage;
        this.userId = userId;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ChangePasswordForm( "changePasswordForm" ) );
    }

    public UserService getUserService()
    {
        return ChronosWebApp.getServices().getUserService();
    }

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

            UserService userService = getUserService();
            User user = userService.get( userId );

            oldPasswordField = new MaxLengthPasswordField( "oldPassword", "Old Password", Login.PASSWORD_LEN );
            newPasswordField = new MaxLengthPasswordField( "newPassword", "New Password", Login.PASSWORD_LEN );
            confirmPasswordField = new MaxLengthPasswordField( "confirmPassword", "Confirm Password", Login.PASSWORD_LEN );

            loginIdLabel = new Label( "loginId", user.getLogin().getName() );

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

            if( oldPasswordField.checkIsNullOrInvalidLength() )
            {
                isRejected = true;
            }

            if( newPasswordField.checkIsNullOrInvalidLength() )
            {
                isRejected = true;
            }

            if( confirmPasswordField.checkIsNullOrInvalidLength() )
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
                    error( "Please make sure that password and confirm password are matched!" );
                    isRejected = true;
                }
            }

            UserService userService = getUserService();
            User user = userService.get( userId );

            if( oldPassword != null && !user.getLogin().getPassword().equals( oldPassword ) )
            {
                error( "Invalid old password!" );
                isRejected = true;
            }

            if( isRejected )
            {
                return;
            }

            user.getLogin().setPassword( password );

            try
            {
                userService.update( user );

                goBackPage.info( "Password Changed Successfully." );

                setResponsePage( goBackPage );
            }
            catch( Exception err )
            {
                error( err.getMessage() );
                LOGGER.error( err.getMessage(), err );
            }
        }
    }
}
