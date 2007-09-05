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
package org.qi4j.chronos.ui.customer;

import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.ui.address.AddressAddEditPanel;
import org.qi4j.chronos.ui.base.AddEditBasePanel;
import org.qi4j.chronos.ui.common.MaxLengthTextField;

public class CustomerAddEditPanel extends AddEditBasePanel
{
    protected MaxLengthTextField fullNameField;
    protected MaxLengthTextField referenceField;

    protected AddressAddEditPanel addressAddEditPanel;

    public CustomerAddEditPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        fullNameField = new MaxLengthTextField( "fullNameField", "Full Name", Customer.NAME_LEN );
        referenceField = new MaxLengthTextField( "referenceField", "Reference", Customer.REFERENCE_LEN );

        addressAddEditPanel = new AddressAddEditPanel( "addressAddEditPanel" );

        add( fullNameField );
        add( referenceField );
        add( addressAddEditPanel );
    }

    public boolean checkIsNotValidated()
    {
        boolean isRejected = false;

        if( fullNameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( referenceField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( addressAddEditPanel.checkIsNotValidated() )
        {
            isRejected = true;
        }

        return isRejected;
    }
}