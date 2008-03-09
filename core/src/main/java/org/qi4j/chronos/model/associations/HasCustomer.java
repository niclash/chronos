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
package org.qi4j.chronos.model.associations;

import java.io.Serializable;
import org.qi4j.association.Association;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.scope.AssociationField;

@Mixins( HasCustomer.HasCustomerMixin.class )
public interface HasCustomer
{
    Association<CustomerEntityComposite> customer();

    final class HasCustomerMixin
        implements HasCustomer, Serializable
    {
        private static final long serialVersionUID = 1L;

        @AssociationField
        private Association<CustomerEntityComposite> customer;

        public final Association<CustomerEntityComposite> customer()
        {
            return customer;
        }
    }
}
