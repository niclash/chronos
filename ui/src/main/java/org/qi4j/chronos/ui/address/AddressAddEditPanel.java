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

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.associations.HasAddress;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public class AddressAddEditPanel extends Panel
{
    private static final long serialVersionUID = 1L;

    public AddressAddEditPanel( String id, ChronosCompoundPropertyModel<? extends HasAddress> addressModel )
    {
        super( id );

        TextField<String> address1Field = new TextField<String>( "firstLine" );
        TextField<String> address2Field = new TextField<String>( "secondLine" );
        TextField<String> zipcodeField = new TextField<String>( "zipCode" );
        TextField<String> countryField = new TextField<String>( "city.name" );
        TextField<String> cityField = new TextField<String>( "city.country.name" );
        TextField<String> stateField = new TextField<String>( "city.state.name" );

        address1Field.setModel( addressModel.<String>bind( "address.firstLine" ) );
        address1Field.setModel( addressModel.<String>bind( "address.secondLine" ) );
        address1Field.setModel( addressModel.<String>bind( "address.zipCode" ) );
        address1Field.setModel( addressModel.<String>bind( "address.city.name" ) );
        address1Field.setModel( addressModel.<String>bind( "address.city.country.name" ) );
        address1Field.setModel( addressModel.<String>bind( "address.city.state.name" ) );

        add( address1Field );
        add( address2Field );
        add( zipcodeField );
        add( countryField );
        add( cityField );
        add( stateField );
    }
}
