/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.model;

import org.qi4j.chronos.model.associations.HasCity;
import org.qi4j.composite.Mixins;
import org.qi4j.injection.scope.PropertyField;
import org.qi4j.injection.scope.This;
import org.qi4j.property.ComputedPropertyInstance;
import org.qi4j.property.Property;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 12, 2008
 * Time: 8:34:25 PM
 */
@Mixins( Address.DescriptorMixin.class )
public interface Address extends Descriptor, AddressLine, ZipCode, HasCity
{
    public final static int ADDRESS1_LEN = 120;
    public final static int ADDRESS2_LEN = 120;
    public final static int ZIPCODE_LEN = 20;
    public final static int COUNTRY_NAME_LEN = 80;
    public final static int CITY_NAME_LEN = 80;
    public final static int STATE_NAME_LEN = 80;

    static final class DescriptorMixin implements Descriptor
    {
        private @This Address address;

        private @PropertyField Property<String> displayValue;

        public Property<String> displayValue()
        {
            return new ComputedPropertyInstance<String>( displayValue )
            {

                private String m_displayValue = address.firstLine().get() + "\n" + address.secondLine().get() + "\n" +
                                                address.city().get().name().get() + "\n" +
                                                address.zipCode().get() + ", " + address.city().get().state().get().name().get() + ", " +
                                                address.city().get().country().get().name().get();

                public String get()
                {
                    return m_displayValue;
                }
            };
        }
    }
}