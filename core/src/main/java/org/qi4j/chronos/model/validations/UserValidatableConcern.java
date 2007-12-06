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
package org.qi4j.chronos.model.validations;

import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.util.ValidatorUtil;
import org.qi4j.composite.scope.ThisCompositeAs;
import org.qi4j.library.framework.validation.AbstractValidatableConcern;
import org.qi4j.library.framework.validation.Validator;

public class UserValidatableConcern extends AbstractValidatableConcern
{
    @ThisCompositeAs private User user;

    protected void isValid( Validator validator )
    {
        ValidatorUtil.isEmptyOrInvalidLength( user.getFirstName(), "First name", User.FIRST_NAME_LEN, validator );
        ValidatorUtil.isEmptyOrInvalidLength( user.getLastName(), "Last name", User.LAST_NAME_LEN, validator );

        if( !user.systemRoleIterator().hasNext() )
        {
            validator.error( true, "Please add at least one system role!" );
        }

        ValidatorUtil.isEmptyOrInvalidLength( user.getLogin().getName(), "LoginId", Login.LOGIN_ID_LEN, validator );
        ValidatorUtil.isEmptyOrInvalidLength( user.getLogin().getPassword(), "Password", Login.PASSWORD_LEN, validator );
    }

    protected String getResourceBundle()
    {
        return null; //no i18n
    }
}
