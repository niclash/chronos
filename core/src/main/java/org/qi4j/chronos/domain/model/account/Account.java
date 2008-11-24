/*
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
package org.qi4j.chronos.domain.model.account;

import org.qi4j.chronos.domain.model.Entity;
import org.qi4j.chronos.domain.model.associations.HasAddress;
import org.qi4j.chronos.domain.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.domain.model.associations.HasProjectRoles;
import org.qi4j.chronos.domain.model.associations.HasProjects;
import org.qi4j.chronos.domain.model.common.enable.Enable;
import org.qi4j.chronos.domain.model.customer.Customer;
import org.qi4j.chronos.domain.model.user.Staff;
import org.qi4j.query.Query;

public interface Account extends Entity<Account>, Enable,
                                 HasAddress, HasProjects, HasProjectRoles, HasPriceRateSchedules
{
    AccountId accountId();

    AccountDetail accountDetail();

    Query<Staff> staffs();

    Query<Customer> customers();
}
