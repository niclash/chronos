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

import java.util.Collections;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PriceRateScheduleAddPage extends PriceRateScheduleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( PriceRateScheduleAddPage.class );

    public PriceRateScheduleAddPage( BasePage goBackPage )
    {
        super( goBackPage );
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "New Price Rate Schedule";
    }

    public void onSubmitting()
    {
        PriceRateScheduleComposite priceRateSchedule = ChronosWebApp.newInstance( PriceRateScheduleComposite.class );

        try
        {
            assignFieldValueToPriceRateSchedule( priceRateSchedule );

            addPriceRateSchedule( priceRateSchedule );

            logInfoMsg( "Price Rate Schedule is added successfully." );

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
        return Collections.EMPTY_LIST.iterator();
    }

    public abstract void addPriceRateSchedule( PriceRateScheduleComposite priceRateScheduleComposite );
}
