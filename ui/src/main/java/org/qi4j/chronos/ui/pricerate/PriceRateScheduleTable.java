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

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.association.SetAssociation;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.service.PriceRateScheduleService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;

public abstract class PriceRateScheduleTable<T extends HasPriceRateSchedules> extends ActionTable<PriceRateScheduleComposite, String>
{
    private PriceRateScheduleDataProvider<T> dataProvider;

    public PriceRateScheduleTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<PriceRateScheduleComposite>( "Delete" )
        {
            public void performAction( List<PriceRateScheduleComposite> priceRateSchedules )
            {
                getPriceRateScheduleService().deletePriceRateSchedule( getHasPriceRateSchedules(), priceRateSchedules );

                info( "Selected price rate schedule(s) are deleted." );
            }
        } );
    }

    private PriceRateScheduleService getPriceRateScheduleService()
    {
        return ChronosWebApp.getServices().getPriceRateScheduleService();
    }

    public AbstractSortableDataProvider<PriceRateScheduleComposite, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new PriceRateScheduleDataProvider<T>()
            {
                public T getHasPriceRateSchedules()
                {
                    return PriceRateScheduleTable.this.getHasPriceRateSchedules();
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, PriceRateScheduleComposite obj )
    {
        final String priceRateScheduleName = obj.name().get();

        item.add( new SimpleLink( "name", obj.name().get() )
        {
            public void linkClicked()
            {
                PriceRateScheduleDetailPage detailPage = new PriceRateScheduleDetailPage( (BasePage) this.getPage() )
                {
                    public PriceRateSchedule getPriceRateSchedule()
                    {
                        return PriceRateScheduleTable.this.getPriceRateSchedule( priceRateScheduleName );
                    }
                };

                setResponsePage( detailPage );
            }
        } );

        item.add( new Label( "currencyLabel", obj.currency().get().getCurrencyCode() ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                PriceRateScheduleEditPage editPage = new PriceRateScheduleEditPage( (BasePage) this.getPage() )
                {
                    public PriceRateScheduleComposite getPriceRateSchedule()
                    {
                        return PriceRateScheduleTable.this.getPriceRateSchedule( priceRateScheduleName );
                    }

                    public void updatePriceRateSchedule( PriceRateScheduleComposite priceRateScheduleComposite )
                    {
                        handleUpdatePriceRateSchedule( priceRateScheduleComposite, priceRateScheduleName );
                    }
                };

                setResponsePage( editPage );
            }
        } );
    }

    private void handleUpdatePriceRateSchedule( PriceRateScheduleComposite updated, String originalName )
    {
        //TODO bp. Since i got no idea how qi4j updates ValueObject, for now, lets have a workaround solution.
        T t = getHasPriceRateSchedules();
        SetAssociation<PriceRateScheduleComposite> priceRateSchedules = t.priceRateSchedules();
        for( PriceRateScheduleComposite priceRateSchedule : priceRateSchedules )
        {

            if( priceRateSchedule.name().get().equals( originalName ) )
            {
                priceRateSchedule.name().set( updated.name().get() );
                priceRateSchedule.currency().set( updated.currency().get() );

                SetAssociation<PriceRateComposite> priceRates = priceRateSchedule.priceRates();
                priceRates.clear();
                priceRates.addAll( updated.priceRates() );
            }
        }
    }

    private PriceRateScheduleComposite getPriceRateSchedule( String name )
    {
        return getPriceRateScheduleService().get( getHasPriceRateSchedules(), name );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Currency", "" );
    }

    public abstract T getHasPriceRateSchedules();
}
