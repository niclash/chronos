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
package org.qi4j.chronos.domain.model.user.assembly;

import org.qi4j.chronos.domain.model.user.UserDetail;
import org.qi4j.composite.Composite;
import org.qi4j.composite.Mixins;
import org.qi4j.injection.scope.Uses;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( UserDetailComposite.UserDetailMixin.class )
interface UserDetailComposite extends UserDetail, Composite
{
    class UserDetailMixin
        implements UserDetail
    {
        @Uses private UserState state;

        public final String firstName()
        {
            return state.firstName().get();
        }

        public final String lastName()
        {
            return state.lastName().get();
        }

        public final void changeName( String firstName, String lastName )
        {
            state.firstName().set( firstName );
            state.lastName().set( lastName );
        }
    }
}
