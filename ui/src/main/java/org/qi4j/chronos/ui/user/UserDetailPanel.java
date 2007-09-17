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
package org.qi4j.chronos.ui.user;

import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.login.LoginUserDetailPanel;

public abstract class UserDetailPanel extends Panel
{
    private SimpleTextField firstNameField;
    private SimpleTextField lastNameField;

    private SimpleTextField genderField;

    private LoginUserDetailPanel loginUserDetailPanel;

    public UserDetailPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        User user = getUser();

        firstNameField = new SimpleTextField( "firstNameField", user.getFirstName() );
        lastNameField = new SimpleTextField( "lastNameField", user.getLastName() );

        genderField = new SimpleTextField( "genderField", user.getGender().toString() );

        loginUserDetailPanel = new LoginUserDetailPanel( "loginUserDetailPanel" )
        {
            public Login getLogin()
            {
                return UserDetailPanel.this.getUser().getLogin();
            }
        };

        add( firstNameField );
        add( lastNameField );
        add( genderField );
        add( loginUserDetailPanel );
    }

    public abstract User getUser();
}