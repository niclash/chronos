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

import java.io.Serializable;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.service.UserService;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosServiceFinderHelper;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.library.framework.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ChangePasswordPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( ChangePasswordPage.class );

    private static final String WICKET_ID_FEEDBACK_PANEL = "feedbackPanel";
    private static final String WICKET_ID_CHANGE_PASSWORD_FORM = "changePasswordForm";

    public ChangePasswordPage( Page aReturnPage )
    {
        add( new FeedbackPanel( WICKET_ID_FEEDBACK_PANEL ) );
        add( new ChangePasswordForm( WICKET_ID_CHANGE_PASSWORD_FORM, new ChangePasswordModel(), aReturnPage ) );
    }

    private class ChangePasswordForm extends Form<ChangePasswordModel>
    {
        private static final long serialVersionUID = 1L;

        private static final String WICKET_ID_LOGIN_ID = "loginId";
        private static final String WICKET_ID_OLD_PASSWORD = "oldPassword";
        private static final String WICKET_ID_NEW_PASSWORD = "newPassword";
        private static final String WICKET_ID_CONFIRM_PASSWORD = "confirmPassword";
        private static final String WICKET_ID_SUBMIT_BUTTON = "submitButton";
        private static final String WICKET_ID_CANCEL_BUTTON = "cancelButton";

        private Button submitButton;

        private Button cancelButton;
        private final Page returnPage;


        public ChangePasswordForm( String id, ChangePasswordModel changePasswordModel, Page aReturnPage )
        {
            super( id );

            returnPage = aReturnPage;

            CompoundPropertyModel<ChangePasswordModel> model =
                new CompoundPropertyModel<ChangePasswordModel>( changePasswordModel );
            setModel( model );

            // Login id
            Label loginIdLabel = newLoginIdLabel();
            add( loginIdLabel );

            IValidator notEmptyString = new StringValidator.MinimumLengthValidator( 1 );

            // Old password
            PasswordTextField oldPasswordField = new PasswordTextField( WICKET_ID_OLD_PASSWORD );
            add( oldPasswordField );
            oldPasswordField.add( notEmptyString );
            oldPasswordField.setRequired( true );

            // New Password
            PasswordTextField newPasswordField = new PasswordTextField( WICKET_ID_NEW_PASSWORD );
            add( newPasswordField );
            newPasswordField.add( notEmptyString );
            newPasswordField.setRequired( true );

            // Confirm Password
            PasswordTextField confirmPasswordField = new PasswordTextField( WICKET_ID_CONFIRM_PASSWORD );
            add( confirmPasswordField );
            confirmPasswordField.add( notEmptyString );
            confirmPasswordField.setRequired( false );

            // Change Password button
            submitButton = new Button( WICKET_ID_SUBMIT_BUTTON, new Model<String>( "Change Password" ) );
            add( submitButton );

            // Cancel button
            cancelButton = new Button( WICKET_ID_CANCEL_BUTTON, new Model<String>( "Cancel" ) );
            add( cancelButton );
        }

        private Label newLoginIdLabel()
        {
            ChronosSession session = (ChronosSession) getSession();
            User user = session.getUser();
            return new Label( WICKET_ID_LOGIN_ID, user.login().get().name().get() );
        }

        @Override
        protected final void delegateSubmit( IFormSubmittingComponent submittingComponent )
        {
            if( submittingComponent == submitButton )
            {
                handleChangePassword();
            }
            else if( submittingComponent == cancelButton )
            {
                setResponsePage( returnPage );
            }
            else
            {
                throw new IllegalArgumentException( "" );
            }
        }

        private void handleChangePassword()
        {
            ChangePasswordModel changePasswordModel = getModelObject();

            String newPassword = changePasswordModel.getNewPassword();
            String confirmPassword = changePasswordModel.getConfirmPassword();

            StringBuilder errorMsgBuilder = validateChangePasswordInput( newPassword, confirmPassword );
            if( errorMsgBuilder.length() != 0 )
            {
                error( errorMsgBuilder.toString() );
                return;
            }

            String oldPassword = changePasswordModel.getOldPassword();
            try
            {
                User user = ChronosSession.get().getUser();

                UserService userService = ChronosServiceFinderHelper.get().findService( UserService.class );
                userService.changePassword( user, oldPassword, newPassword );
                ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

                returnPage.info( "Password was changed successfully." );
                setResponsePage( returnPage );
            }
            catch( ValidationException err )
            {
                error( err.toString() );
            }
            catch( Exception err )
            {
                ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

                error( "Fail to update password" );

                LOGGER.error( err.getLocalizedMessage(), err );
            }
        }

        private StringBuilder validateChangePasswordInput( String newPassword, String confirmPassword )
        {
            StringBuilder errorMsgBuilder = new StringBuilder();
            if( newPassword != null && confirmPassword != null && !( newPassword.equals( confirmPassword ) ) )
            {
                errorMsgBuilder.append( "New password and confirm password is not matched!." );
            }
            return errorMsgBuilder;
        }
    }

    private final static class ChangePasswordModel
        implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private String oldPassword;

        private String newPassword;

        private String confirmPassword;

        public String getOldPassword()
        {
            return oldPassword;
        }

        public void setOldPassword( String oldPassword )
        {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword()
        {
            return newPassword;
        }

        public void setNewPassword( String newPassword )
        {
            this.newPassword = newPassword;
        }

        public String getConfirmPassword()
        {
            return confirmPassword;
        }

        public void setConfirmPassword( String confirmPassword )
        {
            this.confirmPassword = confirmPassword;
        }
    }
}
