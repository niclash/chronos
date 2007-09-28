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

import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PriceRateEditPage extends PriceRateAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( PriceRateEditPage.class );

    public PriceRateEditPage( BasePage goBackPage )
    {
        super( goBackPage );

        initData();
    }

    private void initData()
    {
        assignPriceRateToFieldValue( getPriceRate() );
    }

    public void onSubmitting()
    {
        PriceRateComposite priceRate = getPriceRate();

        try
        {
            assignFieldValueToPriceRate( priceRate );

            updatePriceRate( priceRate );

            logInfoMsg( "Price Rate is updated successfuly." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Edit";
    }

    public String getTitleLabel()
    {
        return "Edit Price Rate";
    }

    public abstract PriceRateComposite getPriceRate();

    public abstract void updatePriceRate( PriceRateComposite priceRate );
}
