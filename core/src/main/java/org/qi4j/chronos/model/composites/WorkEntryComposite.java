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
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.Description;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.Title;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.composites.association.HasComments;
import org.qi4j.chronos.model.mixins.DescriptionMixin;
import org.qi4j.chronos.model.mixins.TimeRangeMixin;
import org.qi4j.chronos.model.mixins.TitleMixin;
import org.qi4j.chronos.model.modifiers.TimeRangeValidationModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { TimeRangeMixin.class, TitleMixin.class, DescriptionMixin.class, PropertiesMixin.class } )
@ModifiedBy({ TimeRangeValidationModifier.class})
public interface WorkEntryComposite extends WorkEntry, Composite
{
}
