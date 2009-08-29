/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.domain.service.authentication;

import org.qi4j.api.common.Optional;
import org.qi4j.chronos.domain.model.account.Account;
import org.qi4j.chronos.domain.model.user.User;

public interface AuthenticationService
{
    /**
     * Authenticate user by account, username and password. Note: the account may be null if the user attempts
     * to login as super admin.
     *
     * @param anAccount account to login, null if user attempts to login as super user.
     * @param aUserName user name.
     * @param apassword user password.
     * @return the logged in user.
     * @throws UserAuthenticationFailException
     *          thrown if user can't be authenticated.
     */
    User authenticate( @Optional Account anAccount, String aUserName, String apassword )
        throws UserAuthenticationFailException;
}
