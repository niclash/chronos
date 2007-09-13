/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.modifiers.RequiredFields;
//import org.qi4j.chronos.model.modifiers.RequiredFieldsValidationModifier;
import org.qi4j.library.general.model.Validatable;


/**
 * Validatable login composite.
 *
 * TODO: Create password validation modifier to make sure password is non-dictionary word and complex enough
 * TODO: Fix the required fields validation as Lifecycle is not part of the interface anymore
 */
//@ModifiedBy( { RequiredFieldsValidationModifier.class } )
//@RequiredFields( { "name", "password" } )
public interface LoginComposite extends Login, Validatable, Composite
{
}