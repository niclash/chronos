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
package org.qi4j.chronos.domain.model.account.assembly;

import org.qi4j.chronos.domain.model.account.AccountDetail;
import org.qi4j.composite.Composite;
import org.qi4j.composite.Mixins;
import org.qi4j.injection.scope.Uses;
import org.qi4j.library.constraints.annotation.MaxLength;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( AccountDetailComposite.AccountDetailMixin.class )
interface AccountDetailComposite extends AccountDetail, Composite
{
    class AccountDetailMixin
        implements AccountDetail
    {
        @Uses private AccountState state;

        public final String name()
        {
            return state.name().get();
        }

        public final String referenceName()
        {
            return state.referenceName().get();
        }

        public final void changeName( @MaxLength( 80 )String aNewName )
        {
            state.name().set( aNewName );
        }

        public final void changeReferenceName( @MaxLength( 80 )String aNewReferenceName )
        {
            state.referenceName().set( aNewReferenceName );
        }
    }
}
