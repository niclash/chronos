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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleLink;

public class LoginUserEditPanel extends LoginUserAbstractPanel
{
    private Label loginIdLabel;
    private SimpleLink changePasswordLink;
    private String userId;

    private CheckBox loginEnabledCheckBox;

    public LoginUserEditPanel( String id, String userId )
    {
        super( id );

        this.userId = userId;

        initComponents();
    }

    private void initComponents()
    {
        loginIdLabel = new Label( "loginId", "" );

        changePasswordLink = new SimpleLink( "changePasswordLink", "Change Password" )
        {
            public void linkClicked()
            {
                handleChangePassword();
            }
        };

        loginEnabledCheckBox = new CheckBox( "loginEnabled", new Model( "false" ) );

        add( loginIdLabel );
        add( changePasswordLink );
        add( loginEnabledCheckBox );
    }

    private void handleChangePassword()
    {
        ChangePasswordPage changePasswordPage = new ChangePasswordPage( (BasePage) this.getPage(), userId );

        setResponsePage( changePasswordPage );
    }

    public boolean checkIsNotValidated()
    {
        return false;
    }

    public void assignLoginToFieldValue( Login login )
    {
        loginIdLabel.setModel( new Model( login.getName() ) );
        loginEnabledCheckBox.setModel( new Model( login.isEnabled() ) );
    }

    public void assignFieldValueToLogin( Login login )
    {
        login.setEnabled( Boolean.parseBoolean( loginEnabledCheckBox.getModelObjectAsString() ) );
    }

}
