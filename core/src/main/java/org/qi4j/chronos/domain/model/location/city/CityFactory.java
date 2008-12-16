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
package org.qi4j.chronos.domain.model.location.city;

import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.State;
import org.qi4j.api.common.Optional;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public interface CityFactory
{
    /**
     * Create city.
     *
     * @param name    city name. Must not be {@code null{.
     * @param state   city state. If {@code null), country must not be {@code null}.
     *                If not {@code null}, country must be {@code null}.
     * @param country city country. If {@code null}, state must not be {@code null}.
     *                If not {@code null}, state must be {@code null}.
     * @return new city.
     * @throws DuplicateCityException Thrown if city exists.
     */
    City create( String name, @Optional State state, @Optional Country country )
        throws DuplicateCityException;
}
