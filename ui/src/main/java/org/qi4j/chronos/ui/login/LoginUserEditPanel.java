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
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasLogin;
import org.qi4j.chronos.ui.wicket.base.ChangePasswordPage;

public abstract class LoginUserEditPanel extends LoginUserAbstractPanel
{
    private static final long serialVersionUID = 1L;

    private Label loginIdLabel;
    private SubmitLink changePasswordLink;
    private CheckBox loginEnabledCheckBox;

    public LoginUserEditPanel( String id )
    {
        super( id );

        loginIdLabel = new Label( "loginId", "" );
        changePasswordLink = newChangePasswordLink();
        loginEnabledCheckBox = new CheckBox( "loginEnabled", new Model( "false" ) );

        add( loginIdLabel );
        add( changePasswordLink );
        add( loginEnabledCheckBox );
    }

    private SubmitLink newChangePasswordLink()
    {
        return new SubmitLink( "changePasswordLink" )
        {
            private static final long serialVersionUID = 1L;

            public void onSubmit()
            {
                ChangePasswordPage changePasswordPage = new ChangePasswordPage( LoginUserEditPanel.this.getPage() )
                {
                    private static final long serialVersionUID = 1L;

                    public User getUser()
                    {
                        return LoginUserEditPanel.this.getUser();
                    }
                };

                LoginUserEditPanel.this.setResponsePage( changePasswordPage );
            }
        };
    }

    public abstract User getUser();

    public boolean checkIsNotValidated()
    {
        return false;
    }

    public void assignLoginToFieldValue( HasLogin hasLogin )
    {
        Login login = hasLogin.login().get();

        loginIdLabel.setModel( new Model( login.name().get() ) );
        loginEnabledCheckBox.setModel( new Model( login.isEnabled().get() ) );
    }

    public void assignFieldValueToLogin( HasLogin hasLogin )
    {
        hasLogin.login().get().isEnabled().set( Boolean.parseBoolean( loginEnabledCheckBox.getModelObjectAsString() ) );
    }
}
