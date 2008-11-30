/*  Copyright 2008 Edward Yakop.
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
package org.qi4j.chronos.domain.model.project;

import org.qi4j.chronos.domain.model.common.legalCondition.LegalCondition;
import org.qi4j.chronos.domain.model.common.period.Period;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.entity.association.SetAssociation;
import org.qi4j.property.Property;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public interface ProjectState
{
    Property<String> name();

    Property<String> referenceName();

    Property<ProjectStatus> projectStatus();

    Property<Period> estimateTime();

    Property<Period> actualTime();

    SetAssociation<PriceRateSchedule> priceRateSchedules();

    SetAssociation<LegalCondition> legalConditions();
}
