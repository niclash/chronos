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

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public final class UserDetailPanel extends Panel
{
    private static final long serialVersionUID = 1L;

    public UserDetailPanel( String id, IModel<User> user )
    {
        super( id );

        ChronosCompoundPropertyModel<IModel<User>> model = new ChronosCompoundPropertyModel<IModel<User>>( user );
        setDefaultModel( model );

        TextField firstNameField = new TextField( "firstName" );
        TextField lastNameField = new TextField( "lastName" );
        TextField genderField = new TextField( "gender" );

        IModel<Login> loginModel = model.bind( "login" );

//        UserLoginDetailPanel userLoginDetailPanel = new UserLoginDetailPanel( "loginUserDetailPanel", loginModel );

        add( firstNameField );
        add( lastNameField );
        add( genderField );
//        add( userLoginDetailPanel );
    }
}