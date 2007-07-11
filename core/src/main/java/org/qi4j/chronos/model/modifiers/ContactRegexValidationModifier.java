/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.model.modifiers;

import java.util.regex.Pattern;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.library.general.model.RegexContactType;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.ValidationException;

/**
 * Matching contact against the regular expression defined for the contact type.
 * If contact doesn't match with pattern, {@link org.qi4j.library.general.model.ValidationException} is thrown.
 */
public final class ContactRegexValidationModifier implements Validatable
{
    @Uses ContactComposite contact;
    @Modifies Validatable next;

    public void validate() throws ValidationException
    {
        String contactValue = contact.getContactValue();

        RegexContactType contactType = contact.getContactType();

        if( contactType != null )
        {
            String regex = contactType.getRegex();
            if( !Pattern.matches( regex, contactValue ) )
            {
                throw new ValidationException( "Contact [" + contact.getContactValue() +
                                               "] does not match pattern [" + regex + "].");
            }
        }

        next.validate();
    }
}
