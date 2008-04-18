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

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.associations.HasLogin;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.ui.common.SimpleTextField;

public abstract class LoginUserDetailPanel extends Panel
{
    private SimpleTextField loginIdField;
    private CheckBox isEnabledCheckBox;

    public LoginUserDetailPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        Login login = getLogin().login().get();

        loginIdField = new SimpleTextField( "loginIdField", login.name().get() );
        isEnabledCheckBox = new CheckBox( "isEnabledCheckBox", new Model( login.isEnabled().get() ) );

        add( loginIdField );
        add( isEnabledCheckBox );
    }

    public abstract HasLogin getLogin();
}
