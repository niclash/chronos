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
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.mixins.HasContactPersonsMixin;
import org.qi4j.chronos.model.mixins.HasLeadProjectAssigneeMixin;
import org.qi4j.chronos.model.mixins.HasLegalConditionsMixin;
import org.qi4j.chronos.model.mixins.HasPriceRateSchedulesMixin;
import org.qi4j.chronos.model.mixins.HasPrimaryContactPersonMixin;
import org.qi4j.chronos.model.mixins.HasProjectAssigneesMixin;
import org.qi4j.chronos.model.mixins.HasProjectStatusMixin;
import org.qi4j.chronos.model.mixins.HasProjectTimeRangeMixin;
import org.qi4j.chronos.model.mixins.NameMixin;
import org.qi4j.chronos.model.mixins.ReferenceMixin;
import org.qi4j.chronos.model.modifiers.ProjectCreationValidationModifier;
import org.qi4j.library.general.model.Validatable;

@ImplementedBy( { NameMixin.class, ReferenceMixin.class, HasProjectTimeRangeMixin.class,
    HasLeadProjectAssigneeMixin.class, HasProjectAssigneesMixin.class,
    HasLegalConditionsMixin.class, HasProjectStatusMixin.class, HasContactPersonsMixin.class,
    HasPrimaryContactPersonMixin.class, HasPriceRateSchedulesMixin.class } )
@ModifiedBy( { ProjectCreationValidationModifier.class } )
public interface ProjectEntityComposite extends Project, Validatable, EntityComposite
{
    
}