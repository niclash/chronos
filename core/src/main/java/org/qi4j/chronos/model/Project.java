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
package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.ContactPersonComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeComposite;

public interface Project
{
    String getProjectName();

    String getFormalReference();

    ProjectStatus getProjectStatus();

    List<ProjectAssigneeComposite> getProjectAssignees();

    ProjectAssigneeComposite getProjectLead();
    
    List<LegalCondition> getLegalConditions();

    ContactPersonComposite getPrimaryContactPerson();

    List<ContactPersonComposite> getCustomerContactPersons();
    
    TimeRange getEstimateTime();

    TimeRange getActualTime();

    List<PriceRateScheduleComposite> getPriceRateSchedules();

    void addProjectAssignee( ProjectAssigneeComposite projectAssignee);

    void removeProjectAssignee( ProjectAssigneeComposite projectAssignee);

    void addLegalCondition(LegalCondition legalCondition);

    void removeLegalCondition(LegalCondition legalCondition);

    void addContactPerson( ContactPersonComposite contactPerson);

    void removeContactPerson( ContactPersonComposite contactPerson);

    void addPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule);

    void removePriceRateSchedule( PriceRateScheduleComposite priceRateSchedule);
}
