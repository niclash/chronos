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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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

    private Page goBackPage;

    public ChangePasswordPage( Page goBackPage )
    {
        this.goBackPage = goBackPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ChangePasswordForm( "changePasswordForm", new ChangePasswordModel() ) );
    }

    private class ChangePasswordForm extends Form<ChangePasswordModel>
    {
        private static final long serialVersionUID = 1L;

        private Button submitButton;

        private Button cancelButton;

        public ChangePasswordForm( String id, ChangePasswordModel changePasswordModel )
        {
            super( id );

            CompoundPropertyModel<ChangePasswordModel> model =
                new CompoundPropertyModel<ChangePasswordModel>( changePasswordModel );

            setModel( model );

            PasswordTextField oldPasswordField = new PasswordTextField( "oldPassword" );
            IModel<String> oldPasswordModel = model.bind( "oldPassword" );
            oldPasswordField.setModel( oldPasswordModel );

            PasswordTextField newPasswordField = new PasswordTextField( "newPassword" );
            IModel<String> newPasswordModel = model.bind( "newPassword" );
            newPasswordField.setModel( newPasswordModel );

            PasswordTextField confirmPasswordField = new PasswordTextField( "confirmPassword" );
            IModel<String> confirmPasswordModel = model.bind( "confirmPassword" );
            confirmPasswordField.setModel( confirmPasswordModel );

            User user = ChronosSession.get().getUser();

            Label<String> loginIdLabel = new Label<String>( "loginId", user.login().get().name().get() );

            submitButton = new Button<String>( "submitButton", new Model<String>( "Change Password" ) );
            cancelButton = new Button<String>( "cancelButton", new Model<String>( "Cancel" ) );

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
            ChangePasswordModel changePasswordModel = getModelObject();

            StringBuilder errorMsgBuilder = new StringBuilder();

            String oldPassword = changePasswordModel.getOldPassword();
            String newPassword = changePasswordModel.getNewPassword();
            String confirmPassword = changePasswordModel.getConfirmPassword();

            if( oldPassword == null )
            {
                errorMsgBuilder.append( "Old password is required. \n" );
            }

            if( newPassword == null )
            {
                errorMsgBuilder.append( "New password is required. \n" );
            }

            if( confirmPassword == null )
            {
                errorMsgBuilder.append( "Confirm password is required. \n" );
            }

            if( newPassword != null && confirmPassword != null && !( newPassword.equals( confirmPassword ) ) )
            {
                errorMsgBuilder.append( "New password and confirm password is not matched!." );
            }

            if( errorMsgBuilder.length() != 0 )
            {
                error( errorMsgBuilder.toString() );
                return;
            }

            try
            {
                User user = ChronosSession.get().getUser();

                UserService userService = ChronosServiceFinderHelper.get().findService( UserService.class );

                userService.changePassword( user, oldPassword, newPassword );

                ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

                goBackPage.info( "Password was changed successfully." );

                setResponsePage( goBackPage );
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
    }

    private final static class ChangePasswordModel implements Serializable
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
