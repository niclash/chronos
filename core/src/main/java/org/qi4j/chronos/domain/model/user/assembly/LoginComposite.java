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

import org.qi4j.chronos.domain.model.user.Login;
import org.qi4j.api.composite.Composite;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.injection.scope.Uses;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( LoginComposite.LoginMixin.class )
interface LoginComposite extends Login, Composite
{
    class LoginMixin
        implements Login
    {
        @Uses private UserState state;

        public final String loginName()
        {
            return state.loginName().get();
        }

        public final void changePassword( String aNewPassword )
        {
            state.loginPassword().set( aNewPassword );
        }

        public final boolean authenticate( String aPassword )
        {
            if( isEnabled() )
            {
                String password = state.loginPassword().get();
                if( password != null )
                {
                    return password.equals( aPassword );
                }
            }

            return false;
        }

        public final boolean isEnabled()
        {
            return state.isLoginEnabled().get();
        }

        public final void enable()
        {
            state.isLoginEnabled().set( true );
        }

        public final void disable()
        {
            state.isLoginEnabled().set( false );
        }
    }
}
