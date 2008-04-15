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
package org.qi4j.chronos.ui.address;

import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;

public class AddressDetailPanel extends Panel
{
    private SimpleTextField address1Field;
    private SimpleTextField address2Field;

    private SimpleTextField zipcodeField;
    private SimpleTextField countryField;
    private SimpleTextField cityField;
    private SimpleTextField stateField;

    public AddressDetailPanel( String id, Address address )
    {
        super( id );

        initComponents( address );
    }

    private void initComponents( Address address )
    {
        address1Field = new SimpleTextField( "address1Field", address.firstLine().get(), true );
        address2Field = new SimpleTextField( "address2Field", address.secondLine().get(), true );
        zipcodeField = new SimpleTextField( "zipcodeField", address.zipCode().get(), true );
        City addressCity = address.city().get();
        countryField = new SimpleTextField( "countryField", addressCity.country().get().name().get(), true );
        cityField = new SimpleTextField( "cityField", addressCity.name().get(), true );
        stateField = new SimpleTextField( "stateField", addressCity.state().get().name().get(), true );

        add( address1Field );
        add( address2Field );
        add( zipcodeField );
        add( countryField );
        add( cityField );
        add( stateField );
    }
}

