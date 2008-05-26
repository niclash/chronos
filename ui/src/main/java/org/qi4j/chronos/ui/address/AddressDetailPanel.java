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
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public class AddressDetailPanel extends Panel
{
    private static final long serialVersionUID = 1L;

    private TextField address1Field;

    private TextField address2Field;
    private TextField zipcodeField;
    private TextField countryField;
    private TextField cityField;
    private TextField stateField;

    public AddressDetailPanel( String id, IModel address )
    {
        super( id );

        ChronosCompoundPropertyModel model = new ChronosCompoundPropertyModel( address );
        setModel( model );

        address1Field = new TextField<String>( "address1Field", model.bind( "firstLine" ) );

        address2Field = new TextField( "address2Field", model.bind( "secondLine" ) );
        zipcodeField = new TextField( "zipcodeField", model.bind( "zipCode" ) );
        countryField = new TextField( "countryField", model.bind( "city.country.name" ) );
        cityField = new TextField( "cityField", model.bind( "city.name" ) );
        stateField = new TextField( "stateField", model.bind( "city.state.name" ) );

        add( address1Field );
        add( address2Field );
        add( zipcodeField );
        add( countryField );
        add( cityField );
        add( stateField );
    }

}

