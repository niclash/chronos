/*
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
package org.qi4j.chronos.model.composites;

import org.qi4j.api.persistence.composite.EntityComposite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.library.general.model.HasCountry;
import org.qi4j.library.general.model.HasState;
import org.qi4j.library.general.model.composites.CityEntityComposite;
import org.qi4j.library.framework.properties.PropertiesMixin;

/**
 * Persistable {@link org.qi4j.library.general.model.composites.CityEntityComposite} linked to
 * {@link org.qi4j.library.general.model.composites.StateEntityComposite} and
 * {@link org.qi4j.library.general.model.composites.CountryEntityComposite}
 * <p/>
 * ChronosCityEntityComposite is an entity.
 */
@ImplementedBy( { PropertiesMixin.class } )
public interface ChronosCityEntityComposite extends CityEntityComposite, HasState, HasCountry, EntityComposite
{
}
