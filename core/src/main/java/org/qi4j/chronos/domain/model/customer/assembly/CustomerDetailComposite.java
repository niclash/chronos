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
package org.qi4j.chronos.domain.model.customer.assembly;

import org.qi4j.api.common.Optional;
import org.qi4j.api.composite.TransientComposite;
import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.chronos.domain.model.customer.CustomerDetail;
import org.qi4j.chronos.domain.model.customer.CustomerState;
import org.qi4j.chronos.domain.model.location.address.Address;

@Mixins( CustomerDetailComposite.CustomerDetailMixin.class )
interface CustomerDetailComposite extends CustomerDetail, TransientComposite
{
    public class CustomerDetailMixin
        implements CustomerDetail
    {
        @Uses private CustomerState state;

        public String name()
        {
            return state.name().get();
        }

        public void changeName( String name )
        {
            state.name().set( name );
        }

        @Optional
        public String referenceName()
        {
            return state.referenceName().get();
        }

        public void changeReferenceName( @Optional String newReferenceName )
        {
            state.referenceName().set( newReferenceName );
        }

        public Address address()
        {
            return state.address().get();
        }

        public void changeAddress( Address address )
        {
            state.address().set( address );
        }
    }
}
