/*
 * Copyright (c) 2007 Lan Boon Ping. All Rights Reserved.
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.domain.model.customer;

import org.qi4j.chronos.domain.model.Entity;
import org.qi4j.chronos.domain.model.common.enable.Enable;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.user.contactPerson.ContactPerson;
import org.qi4j.api.query.Query;

public interface Customer extends Entity<Customer>, Enable
{
    CustomerId customerId();

    CustomerDetail customerDetail();

    Query<ContactPerson> contactPersons();

    void addContactPerson( ContactPerson contactPerson );

    void removeContactPerson( ContactPerson contactPerson );

    Query<PriceRateSchedule> priceRateSchedules();
}