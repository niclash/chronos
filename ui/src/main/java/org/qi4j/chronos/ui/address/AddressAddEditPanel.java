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

public class AddressAddEditPanel extends Panel
{
    private static final long serialVersionUID = 1L;

    public AddressAddEditPanel( String id )
    {
        super( id );

        TextField address1Field = new TextField( "firstLine" );
        TextField address2Field = new TextField( "secondLine" );
        TextField zipcodeField = new TextField( "zipCode" );
        TextField countryField = new TextField( "city.name" );
        TextField cityField = new TextField( "city.country.name" );
        TextField stateField = new TextField( "city.state.name" );

        add( address1Field );
        add( address2Field );
        add( zipcodeField );
        add( countryField );
        add( cityField );
        add( stateField );
    }
}
