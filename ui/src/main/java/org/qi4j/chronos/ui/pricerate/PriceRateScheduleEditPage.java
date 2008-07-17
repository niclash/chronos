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
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceRateScheduleEditPage extends PriceRateScheduleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( PriceRateScheduleEditPage.class );

    public PriceRateScheduleEditPage( Page basePage, final IModel<PriceRateSchedule> priceRateScheduleModel )
    {
        super( basePage, priceRateScheduleModel );

/*
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( priceRateScheduleId, PriceRateScheduleEntityComposite.class );
                    }
                }
            )
        );

        bindPropertyModel( getModel() );
*/
        hideSelectPriceRateScheduleLink();
    }

    protected void handleSubmitClicked( IModel iModel )
    {
/*
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            final PriceRateSchedule priceRateSchedule = (PriceRateSchedule) getModelObject();

            priceRateSchedule.priceRates().clear();
            for( PriceRate priceRate : priceRateList )
            {
                priceRate.currency().set( priceRateSchedule.currency().get() );
                priceRateSchedule.priceRates().add( priceRate );
            }

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

            logInfoMsg( "Success" );
            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( "error " + err.getMessage() );
            LOGGER.error( err.getLocalizedMessage(), err );
        }
*/
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Project";
    }

    public Iterator<PriceRate> getInitPriceRateIterator()
    {
        return getPriceRateSchedule().priceRates().iterator();
    }

    public PriceRateSchedule getPriceRateSchedule()
    {
        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( (PriceRateSchedule) getDefaultModelObject() );
    }
}
