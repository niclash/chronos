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
package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.modifiers.ContactRegexValidationModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Contact;
import org.qi4j.library.general.model.RegexContactType;
import org.qi4j.library.general.model.Validatable;

/**
 * Validatable Contact composite. Contact must have {@link org.qi4j.library.general.model.ContactType}.
 * E.g. email contact would be john.smith@gmail.com
 * 
 * Contact does not extend {@link org.qi4j.api.persistence.composite.EntityComposite} because
 * Contact is bound to {@link org.qi4j.chronos.model.ContactPerson}.
 * When contact person is removed, the contact is removed as well
 *
 * TODO: Fix the validation modifier as Lifecycle is not part of the interface anymore
 */
@ModifiedBy( { ContactRegexValidationModifier.class } )
@ImplementedBy( { PropertiesMixin.class } )
public interface ContactComposite extends Contact<RegexContactType>, Validatable, Composite
{
}
