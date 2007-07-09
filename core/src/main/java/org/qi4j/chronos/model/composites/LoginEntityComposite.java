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

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.persistence.composite.EntityComposite;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.modifiers.RequiredFields;
import org.qi4j.chronos.model.modifiers.RequiredFieldsValidationModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Validatable;


/**
 * Login entity storing information like login-name, password and whether the login is enabled.
 * <p/>
 * RequiredFields for Login are:
 * <li>
 * <ul>identity (login-name)
 * <ul>password
 * </li>
 * <p/>
 * TODO:
 * Create password validation modifier to make sure password is non-dictionary word and complex enough
 */
@ModifiedBy( { RequiredFieldsValidationModifier.class } )
@ImplementedBy( { PropertiesMixin.class } )
@RequiredFields( { "identity", "password" } )
public interface LoginEntityComposite extends Login, Validatable, EntityComposite
{
}