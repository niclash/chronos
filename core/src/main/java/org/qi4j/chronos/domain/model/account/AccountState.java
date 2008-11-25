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
package org.qi4j.chronos.domain.model.account;

import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.location.address.Address;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.user.Staff;
import org.qi4j.composite.Optional;
import org.qi4j.entity.association.Association;
import org.qi4j.entity.association.SetAssociation;
import org.qi4j.property.Property;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public interface AccountState
{
    Property<String> name();

    @Optional Property<String> referenceName();

    @Optional Association<Address> address();

    SetAssociation<Staff> staffs();

    SetAssociation<Customer> customers();

    SetAssociation<Project> projects();

    SetAssociation<ProjectRole> projectRoles();

    SetAssociation<PriceRateSchedule> priceRateSchedules();
}