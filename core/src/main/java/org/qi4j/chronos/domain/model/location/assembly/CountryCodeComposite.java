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
package org.qi4j.chronos.domain.model.location.assembly;

import org.qi4j.chronos.domain.model.location.country.CountryCode;
import org.qi4j.chronos.domain.model.location.country.CountryState;
import org.qi4j.api.composite.Composite;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.injection.scope.Uses;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( CountryCodeComposite.CountryCodeMixin.class )
interface CountryCodeComposite extends CountryCode, Composite
{
    class CountryCodeMixin
        implements CountryCode
    {
        @Uses private CountryState state;

        public final String numeric()
        {
            return state.countryCodeNumeric().get();
        }

        public final String alpha2()
        {
            return state.countryCodeAlpha2().get();
        }

        public final String alpha3()
        {
            return state.countryCodeAlpha3().get();
        }
    }
}
