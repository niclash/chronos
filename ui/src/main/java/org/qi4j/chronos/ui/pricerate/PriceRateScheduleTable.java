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

import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PriceRateScheduleTable<T extends HasPriceRateSchedules> extends ActionTable<PriceRateSchedule>
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( PriceRateScheduleTable.class );

    private final static String DELETE_FAIL = "deleteFailed";
    private final static String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_ACTION = "deleteAction";

    private static final String[] COLUMN_NAMES = { "Name", "Currency", "" };

    public PriceRateScheduleTable( String aWicketId, IModel<HasPriceRateSchedules> hasPriceRateSchedules, PriceRateScheduleDataProvider dataProvider )
    {
        super( aWicketId, hasPriceRateSchedules, dataProvider, COLUMN_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction( new DeleteAction<PriceRateSchedule>( getString( DELETE_ACTION ) )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<PriceRateSchedule> priceRateSchedules )
            {
                handleDelete( priceRateSchedules );
                info( getString( DELETE_SUCCESS ) );
            }
        }
        );
    }

    /**
     * Delete PriceRateSchedule from HasPriceRateSchedules i.e. remove from Customer but not from Account
     *
     * @param priceRateSchedules
     */
    private void handleDelete( List<PriceRateSchedule> priceRateSchedules )
    {
        try
        {
            HasPriceRateSchedules hasPriceRateSchedules = (HasPriceRateSchedules) getDefaultModelObject();

            for( PriceRateSchedule priceRateSchedule : priceRateSchedules )
            {
                hasPriceRateSchedules.priceRateSchedules().remove( priceRateSchedule );
                ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().remove( priceRateSchedule );
            }
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
            error( getString( DELETE_FAIL ) );
        }
    }


    public void populateItems( Item<PriceRateSchedule> item )
    {
        final PriceRateSchedule priceRateSchedule = item.getModelObject();

        final String priceRateScheduleName = priceRateSchedule.name().get();
        final String priceRateScheduleId = priceRateSchedule.identity().get();

        item.add( new SimpleLink( "name", priceRateScheduleName )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                PriceRateScheduleDetailPage detailPage =
                    new PriceRateScheduleDetailPage( this.getPage(), priceRateScheduleId );
                setResponsePage( detailPage );
            }
        }
        );
        item.add( new Label( "currencyLabel", priceRateSchedule.currency().get().getCurrencyCode() ) );
        item.add( new SimpleLink( "editLink", "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
/*
                    PriceRateScheduleEditPage editPage =
                        new PriceRateScheduleEditPage( (BasePage) this.getPage(), priceRateScheduleId );
                    setResponsePage( editPage );
*/
            }
        }
        );
    }
}
