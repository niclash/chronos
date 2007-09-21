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
package org.qi4j.chronos.ui.login;

import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.ui.common.MaxLengthPasswordField;
import org.qi4j.chronos.ui.common.MaxLengthTextField;

public class LoginUserAddPanel extends LoginUserAbstractPanel
{
    private MaxLengthTextField loginIdField;

    private MaxLengthPasswordField passwordField;
    private MaxLengthPasswordField confirmPasswordField;

    public LoginUserAddPanel( String id )
    {
        super( id );

        loginIdField = new MaxLengthTextField( "loginIdField", "Login Id", Login.LOGIN_ID_LEN );
        passwordField = new MaxLengthPasswordField( "passwordField", "Password", Login.PASSWORD_LEN );
        confirmPasswordField = new MaxLengthPasswordField( "confirmPasswordField", "Confirm Password", Login.PASSWORD_LEN );

        passwordField.setRequired( false );
        confirmPasswordField.setRequired( false );

        add( loginIdField );
        add( passwordField );
        add( confirmPasswordField );
    }

    public boolean checkIsNotValidated()
    {
        boolean isRejected = false;

        if( loginIdField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( passwordField.checkIsNullOrInvalidLength() )
        {
            isRejected = true;
        }

        if( confirmPasswordField.checkIsNullOrInvalidLength() )
        {
            isRejected = true;
        }

        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if( password != null && confirmPassword != null )
        {
            if( !password.equals( confirmPassword ) )
            {
                error( "Please make sure that password and confirm password are matched!" );
                isRejected = true;
            }
        }

        return isRejected;
    }

    public void assignFieldValueToLogin( Login login )
    {
        login.setName( loginIdField.getText() );
        login.setPassword( passwordField.getText() );
        login.setEnabled( true );
    }

    public void assignLoginToFieldValue( Login login )
    {
        //printSomething here
    }
}
