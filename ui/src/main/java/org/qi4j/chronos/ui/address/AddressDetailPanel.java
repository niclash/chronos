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
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public class AddressDetailPanel extends Panel<IModel<Address>>
{
    private static final long serialVersionUID = 1L;

    public AddressDetailPanel( String id, IModel<Address> address )
    {
        super( id );

        ChronosCompoundPropertyModel<IModel<Address>> model = new
            ChronosCompoundPropertyModel<IModel<Address>>( address );

        setModel( model );

        TextField<String> firstLineField = new TextField<String>( "firstLine" );
        TextField<String> secondLineField = new TextField<String>( "secondLine" );
        TextField<String> zipCodeField = new TextField<String>( "zipCode" );
        TextField<String> countryField = new TextField<String>( "city.country.name" );
        TextField<String> cityField = new TextField<String>( "city.name" );
        TextField<String> stateField = new TextField<String>( "city.state.name" );

        add( firstLineField );
        add( secondLineField );
        add( zipCodeField );
        add( countryField );
        add( cityField );
        add( stateField );
    }
}

