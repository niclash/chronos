/*  Copyright 2007 Niclas Hedhman.
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

import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.Salary;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { PropertiesMixin.class } )
public interface StaffComposite extends UserComposite, TimeRange, Salary, PriceRateComposite, PersistentComposite
{
}