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
package org.qi4j.chronos.ui.pricerate;

import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDateField;
import org.qi4j.chronos.ui.util.ValidatorUtil;

public abstract class PriceRateScheduleAddEditPage<T extends HasPriceRateSchedules> extends AddEditBasePage
{
    private MaxLengthTextField nameField;

    private SimpleDateField fromDateField;
    private SimpleDateField toDateField;

    public PriceRateScheduleAddEditPage( BasePage goBackPage )
    {
        super( goBackPage );
    }

    public final void initComponent( Form form )
    {
        nameField = new MaxLengthTextField( "name", "Name", PriceRateSchedule.NAME_LEN );

        fromDateField = new SimpleDateField( "fromDateField" );
        toDateField = new SimpleDateField( "toDateField" );

        form.add( nameField );
        form.add( fromDateField );
        form.add( toDateField );
    }

    public final void handleSubmit()
    {
        boolean isRejected = false;

        if( nameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( ValidatorUtil.isAfterDate( fromDateField.getDate(), toDateField.getDate(),
                                       "FromDate", "ToDate", this ) )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public abstract T getHasPriceRateSchedule();

    protected void assignFieldValueToPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        priceRateSchedule.setName( nameField.getText() );
        priceRateSchedule.setStartTime( fromDateField.getDate() );
        priceRateSchedule.setEndTime( toDateField.getDate() );
    }

    protected void assignPriceRateScheduleToFieldValue( PriceRateSchedule priceRateSchedule )
    {
        nameField.setText( priceRateSchedule.getName() );
        fromDateField.setDate( priceRateSchedule.getStartTime() );
        toDateField.setDate( priceRateSchedule.getEndTime() );
    }

    public abstract void onSubmitting();
}
