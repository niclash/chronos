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

import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.util.ValidatorUtil;
import org.qi4j.composite.scope.ThisCompositeAs;
import org.qi4j.library.framework.validation.AbstractValidatableConcern;
import org.qi4j.library.framework.validation.Validator;
import org.qi4j.library.general.model.Address;

public final class AccountValidatableConcern extends AbstractValidatableConcern
{
    @ThisCompositeAs private Account account;

    protected void isValid( Validator validator )
    {
        ValidatorUtil.isEmptyOrInvalidLength( account.getName(), "Account Name", Account.NAME_LEN, validator );
        ValidatorUtil.isEmptyOrInvalidLength( account.getReference(), "Account reference", Account.REFERENCE_LEN, validator );

        ValidatorUtil.isEmptyOrInvalidLength( account.getAddress().getFirstLine(), "Address 1", Address.ADDRESS1_LEN, validator );
        ValidatorUtil.isEmptyOrInvalidLength( account.getAddress().getCity().getCountry().getName(), "Country", Address.COUNTRY_NAME_LEN, validator );
    }

    protected String getResourceBundle()
    {
        return null; //no i18n
    }
}
