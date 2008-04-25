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

import org.qi4j.chronos.ui.wicket.base.AddEditBasePanel;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.associations.HasAddress;
import org.apache.wicket.model.IModel;

public class AddressAddEditPanel extends AddEditBasePanel
{
    private MaxLengthTextField address1Field;
    private MaxLengthTextField address2Field;

    private MaxLengthTextField zipcodeField;

    private MaxLengthTextField countryField;
    private MaxLengthTextField cityField;
    private MaxLengthTextField stateField;

    public AddressAddEditPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        address1Field = new MaxLengthTextField( "address1Field", "Address 1", Address.ADDRESS1_LEN );
        address2Field = new MaxLengthTextField( "address2Field", "Address 2", Address.ADDRESS2_LEN );
        zipcodeField = new MaxLengthTextField( "zipcodeField", "Zip code", Address.ZIPCODE_LEN );
        countryField = new MaxLengthTextField( "countryField", "Country", Address.COUNTRY_NAME_LEN );
        cityField = new MaxLengthTextField( "cityField", "City", Address.CITY_NAME_LEN );
        stateField = new MaxLengthTextField( "stateField", "State", Address.STATE_NAME_LEN );

        add( address1Field );
        add( address2Field );
        add( zipcodeField );
        add( countryField );
        add( cityField );
        add( stateField );
    }

    public void bindPropertyModel( IModel iModel )
    {
        address1Field.setModel( new CompositeModel( iModel, "firstLine") );
        address2Field.setModel( new CompositeModel( iModel, "secondLine") );
        zipcodeField.setModel( new CompositeModel( iModel, "zipCode") );
        IModel city = new CompositeModel( iModel, "city" );
        cityField.setModel( new NameModel( city ) );
        countryField.setModel( new NameModel( new CompositeModel( city, "country" ) ) );
        stateField.setModel( new NameModel( new CompositeModel( city, "state" ) ) );
    }
    
    public void assignFieldValueToAddress( HasAddress hasAddress )
    {
        hasAddress.address().get().firstLine().set( address1Field.getText() );
        hasAddress.address().get().secondLine().set( address2Field.getText() );

        hasAddress.address().get().zipCode().set( zipcodeField.getText() );
        City addressCity = hasAddress.address().get().city().get();
        addressCity.name().set( cityField.getText() );
        addressCity.state().get().name().set( stateField.getText() );
        addressCity.country().get().name().set( countryField.getText() );
    }

    public void assignAddressToFieldValue( HasAddress hasAddress )
    {
        address1Field.setText( hasAddress.address().get().firstLine().get() );
        address2Field.setText( hasAddress.address().get().secondLine().get() );

        zipcodeField.setText( hasAddress.address().get().zipCode().get() );
        City addressCity = hasAddress.address().get().city().get();
        cityField.setText( addressCity.name().get() );

        stateField.setText( addressCity.state().get().name().get() );
        countryField.setText( addressCity.country().get().name().get() );
    }

    public MaxLengthTextField getAddress1Field()
    {
        return address1Field;
    }

    public MaxLengthTextField getAddress2Field()
    {
        return address2Field;
    }

    public MaxLengthTextField getZipcodeField()
    {
        return zipcodeField;
    }

    public MaxLengthTextField getCountryField()
    {
        return countryField;
    }

    public MaxLengthTextField getCityField()
    {
        return cityField;
    }

    public MaxLengthTextField getStateField()
    {
        return stateField;
    }

    public boolean checkIsNotValidated()
    {
        boolean isRejected = false;

        if( address1Field.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( countryField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        return isRejected;
    }
}
