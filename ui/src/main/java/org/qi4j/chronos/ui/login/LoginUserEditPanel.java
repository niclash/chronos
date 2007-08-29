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
import org.qi4j.chronos.ui.common.SimpleLink;

public class LoginUserEditPanel extends LoginUserAbstractPanel
{
    private Label loginIdLabel;
    private SimpleLink changePasswordLink;

    public LoginUserEditPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        loginIdLabel = new Label( "loginId", "" );
    }

    public boolean checkIsNotValidated()
    {
        //TODO fixme this.
        return false;
    }

}
