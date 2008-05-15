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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceRateScheduleEditPage extends PriceRateScheduleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( PriceRateScheduleEditPage.class );
    private static final String TITLE_LABEL = "editPageTitleLabel";
    private static final String SUBMIT_BUTTON = "editPageSubmitButton";
    private static final String UPDATE_SUCCESS = "updateSuccessful";
    private static final String UPDATE_FAIL = "updateFailed";
    private transient UnitOfWork sharedUnitOfWork;

    public PriceRateScheduleEditPage( Page basePage, final String priceRateScheduleId )
    {
        super( basePage );

        final UnitOfWork unitOfWork = getUnitOfWork();
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return unitOfWork.find( priceRateScheduleId, PriceRateScheduleEntityComposite.class );
                    }
                }
            )
        );

        setSharedUnitOfWork( unitOfWork );
        hideSelectPriceRateScheduleLink();
        bindPropertyModel( getModel() );
    }

    public String getSubmitButtonValue()
    {
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }

    public void onSubmitting()
    {
        final UnitOfWork unitOfWork = getSharedUnitOfWork();
        try
        {
            final PriceRateSchedule priceRateSchedule = (PriceRateSchedule) getModelObject();

            priceRateSchedule.priceRates().clear();
            for( PriceRate priceRate : priceRateList )
            {
                priceRate.currency().set( priceRateSchedule.currency().get() );
                priceRateSchedule.priceRates().add( priceRate );
            }
            unitOfWork.complete();

            logInfoMsg( getString( UPDATE_SUCCESS ) );
            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            unitOfWork.reset();

            logErrorMsg( getString( UPDATE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
        catch( Exception err)
        {
            unitOfWork.reset();
            
            logErrorMsg( getString( UPDATE_FAIL, new Model( err ) ) );
            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }

    public void setSharedUnitOfWork( final UnitOfWork unitOfWork )
    {
        this.sharedUnitOfWork = unitOfWork;
    }

    public UnitOfWork getSharedUnitOfWork()
    {
        return this.sharedUnitOfWork;
    }
    
    public Iterator<PriceRate> getInitPriceRateIterator()
    {
        return getPriceRateSchedule().priceRates().iterator();
    }

    public PriceRateSchedule getPriceRateSchedule()
    {
        return getUnitOfWork().dereference( (PriceRateSchedule) getModelObject() );
    }
}
