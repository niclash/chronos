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
import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntityComposite;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.CurrencyUtil;
import org.qi4j.entity.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PriceRateScheduleAddPage extends PriceRateScheduleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( PriceRateScheduleAddPage.class );
    private static final String ADD_FAIL = "addFailed";
    private static final String ADD_SUCCESS = "addSuccessful";
    private static final String SUBMIT_BUTTON = "addPageSubmitButton";
    private static final String TITLE_LABEL = "addPageTitleLabel";

    public PriceRateScheduleAddPage( Page goBackPage )
    {
        super( goBackPage );

        bindModel();
    }

    private void bindModel()
    {
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        final PriceRateSchedule priceRateSchedule =
                            ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().newEntityBuilder( PriceRateScheduleEntityComposite.class ).newInstance();
                        priceRateSchedule.currency().set( CurrencyUtil.getDefaultCurrency() );

                        return priceRateSchedule;
                    }
                }
            )
        );

        bindPropertyModel( getModel() );
        addNewPriceRate();
        updatePriceRateListView();
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
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            final Account account = unitOfWork.dereference( getAccount() );
            final PriceRateSchedule priceRateSchedule = (PriceRateSchedule) getModelObject();

            for( PriceRate priceRate : priceRateList )
            {
                priceRate.currency().set( priceRateSchedule.currency().get() );
                priceRateSchedule.priceRates().add( priceRate );
            }
            account.priceRateSchedules().add( priceRateSchedule );
            HasPriceRateSchedules hasPriceRateSchedules =
                unitOfWork.dereference( PriceRateScheduleAddPage.this.getHasPriceRateSchedules() );
            hasPriceRateSchedules.priceRateSchedules().add( priceRateSchedule );

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

            logInfoMsg( getString( ADD_SUCCESS ) );
            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( getString( ADD_FAIL, new Model( err ) ) );
            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }

    public Iterator<PriceRate> getInitPriceRateIterator()
    {
        return Collections.EMPTY_LIST.iterator();
    }

    public abstract HasPriceRateSchedules getHasPriceRateSchedules();
}
