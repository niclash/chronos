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
package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasContacts;
import org.qi4j.chronos.model.associations.HasLogin;
import org.qi4j.chronos.model.associations.HasSystemRoles;
import org.qi4j.library.general.model.Person;


/**
 * User here is a generic interface instead of entity or value object.
 * <p/>
 * The reason why user is not an entity is because in Chronos system,
 * user must belong to one of the categories below:
 * <li>
 * <ol>Staff
 * <ol>Contact person (customer)
 * <ol>External consultant
 * </li>
 */
public interface User extends Person, HasLogin, HasContacts, HasSystemRoles
{
    public final static int FIRST_NAME_LEN = 80;
    public final static int LAST_NAME_LEN = 80;

}