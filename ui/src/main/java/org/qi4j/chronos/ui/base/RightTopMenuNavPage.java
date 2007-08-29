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
package org.qi4j.chronos.ui.base;

import org.apache.wicket.markup.html.link.Link;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.ui.ChronosSession;
import org.qi4j.chronos.ui.login.ChangePasswordPage;

public abstract class RightTopMenuNavPage extends BasePage
{
    public RightTopMenuNavPage()
    {
        add( new Link( "changePasswordLink" )
        {
            public void onClick()
            {
                handleChangePassword();
            }
        }
        );
    }

    private void handleChangePassword()
    {
        ChronosSession session = (ChronosSession) ChronosSession.get();

        Identity identity = (Identity) session.getUser();

        setResponsePage( new ChangePasswordPage( this, identity.getIdentity() ) );
    }
}
