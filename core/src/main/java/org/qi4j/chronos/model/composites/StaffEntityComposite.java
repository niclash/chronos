/*
 * Copyright 2007 Sianny Halim. All Rights Reserved.
 * Copyright 2007 Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.persistence.composite.EntityComposite;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.Salary;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasProjectAssignees;
import org.qi4j.chronos.model.modifiers.RequiredFields;
import org.qi4j.chronos.model.modifiers.RequiredFieldsValidationModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Validatable;

/**
 * Staff works for the company and hence contains more information on top of the basic user information provided in
 * {@link org.qi4j.chronos.model.User} like:
 * 
 * <li>
 * <ol>Start and end employment date
 * <ol>Salary
 * <ol>Basic price rate
 * <ol>Projects that this staff is working on
 * </li>
 */
@ModifiedBy( { RequiredFieldsValidationModifier.class } )
@ImplementedBy( { PropertiesMixin.class } )
@RequiredFields( { "firstName", "lastName", "gender", "startTime" } )
public interface StaffEntityComposite extends User, TimeRange, Salary, PriceRate, HasProjectAssignees, Validatable, EntityComposite
{
}