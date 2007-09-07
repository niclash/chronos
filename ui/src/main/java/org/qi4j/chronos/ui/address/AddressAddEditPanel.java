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

import org.qi4j.chronos.ui.base.AddEditBasePanel;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.library.general.model.Address;

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

    public void assignFieldValueToAddress( Address address )
    {
        address.setFirstLine( address1Field.getText() );
        address.setSecondLine( address2Field.getText() );

        address.setZipCode( zipcodeField.getText() );
        address.getCity().setName( cityField.getText() );

        address.getCity().getState().setName( stateField.getText() );
        address.getCity().getCountry().setName( countryField.getText() );
    }

    public void assignAddressToFieldValue( Address address )
    {
        address1Field.setText( address.getFirstLine() );
        address2Field.setText( address.getSecondLine() );

        zipcodeField.setText( address.getZipCode() );
        cityField.setText( address.getCity().getName() );

        stateField.setText( address.getCity().getState().getName() );
        countryField.setText( address.getCity().getCountry().getName() );
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
