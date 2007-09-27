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

import java.util.Iterator;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PriceRateScheduleEditPage extends PriceRateScheduleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( PriceRateScheduleEditPage.class );

    public PriceRateScheduleEditPage( BasePage basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        PriceRateScheduleComposite priceRateSchedule = getPriceRateSchedule();

        assignPriceRateScheduleToFieldValue( priceRateSchedule );
    }

    public String getSubmitButtonValue()
    {
        return "Update";
    }

    public String getTitleLabel()
    {
        return "Update Price Rate Schedule";
    }

    public void onSubmitting()
    {
        PriceRateScheduleComposite priceRateSchedule = getPriceRateSchedule();

        try
        {
            assignFieldValueToPriceRateSchedule( priceRateSchedule );

            updatePriceRateSchedule( priceRateSchedule );

            logInfoMsg( "Price Rate Schedule is updated successfuly." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public Iterator<PriceRateComposite> getInitPriceRateIterator()
    {
        return ChronosWebApp.getServices().getPriceRateService().findAll( getPriceRateSchedule() ).iterator();
    }

    public abstract PriceRateScheduleComposite getPriceRateSchedule();

    public abstract void updatePriceRateSchedule( PriceRateScheduleComposite priceRateScheduleComposite );
}
