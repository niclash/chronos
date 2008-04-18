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

import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.associations.HasLogin;

public abstract class LoginUserAbstractPanel extends Panel
{
    public LoginUserAbstractPanel( String id )
    {
        super( id );
    }

    public abstract boolean checkIsNotValidated();

    public abstract void assignFieldValueToLogin( HasLogin login );

    public abstract void assignLoginToFieldValue( HasLogin login );
}
