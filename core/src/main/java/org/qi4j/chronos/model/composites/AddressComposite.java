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
import org.qi4j.library.framework.validation.Validatable;
import org.qi4j.library.general.model.Address;
import org.qi4j.library.general.model.Descriptor;

/**
 * Address composite provides services including:
 * {@link org.qi4j.library.framework.validation.Validatable} and {@link org.qi4j.library.general.model.Descriptor}.
 * <p/>
 * Address doesn't extend {@link org.qi4j.api.persistence.EntityComposite} because
 * Address is bound to {@link org.qi4j.chronos.model.Customer}.
 * Address is removed when {@link org.qi4j.chronos.model.Customer} is removed.
 * <p/>
 * TODO: Fix the required fields validation as Lifecycle is not part of the interface anymore
 */
//@Concerns( { RequiredFieldsValidationModifier.class } )
//@Mixins( { AddressDescriptorMixin.class } )
//@RequiredFields( { "firstLine", "zipCode", "city" } )
public interface AddressComposite extends Address, Validatable, Descriptor, Composite
{
}