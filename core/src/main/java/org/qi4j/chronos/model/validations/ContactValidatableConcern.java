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

import static org.qi4j.chronos.util.ValidatorUtil.isEmptyOrInvalidLength;
import org.qi4j.injection.scope.This;
import org.qi4j.library.validation.AbstractValidatableConcern;
import org.qi4j.library.validation.Validator;
import org.qi4j.library.general.model.Contact;

public class ContactValidatableConcern extends AbstractValidatableConcern
{
    @This Contact contact;

    protected void isValid( Validator validator )
    {
        isEmptyOrInvalidLength( contact.contactType().get(), "Contact Type", Contact.CONTACT_TYPE_LEN, validator );
        isEmptyOrInvalidLength( contact.contactValue().get(), "Contact Value", Contact.CONTACT_VALUE_LEN, validator );
    }

    protected String getResourceBundle()
    {
        return null; //no i18n
    }
}
