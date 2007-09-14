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
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.util.DateUtil;

public abstract class PriceRateScheduleTable<T extends HasPriceRateSchedules> extends ActionTable<PriceRateScheduleComposite, String>
{
    private PriceRateScheduleDataProvider dataProvider;

    public PriceRateScheduleTable( String id )
    {
        super( id );
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
        final String priceRateScheduleName = obj.getName();

        item.add( new SimpleLink( "name", obj.getName() )
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

        item.add( new Label( "fromDate", DateUtil.format( "dd MMM yyy", obj.getStartTime() ) ) );
        item.add( new Label( "toDate", DateUtil.format( "dd MMM yyy", obj.getEndTime() ) ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                PriceRateScheduleEditPage<T> editPage = new PriceRateScheduleEditPage<T>( (BasePage) this.getPage() )
                {
                    public T getHasPriceRateSchedule()
                    {
                        return PriceRateScheduleTable.this.getHasPriceRateSchedules();
                    }

                    public PriceRateScheduleComposite getPriceRateSchedule()
                    {
                        return PriceRateScheduleTable.this.getPriceRateSchedule( priceRateScheduleName );
                    }
                };

                setResponsePage( editPage );
            }
        } );
    }

    private PriceRateScheduleComposite getPriceRateSchedule( String name )
    {
        return ChronosWebApp.getServices().getPriceRateScheduleService().get( getHasPriceRateSchedules(), name );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "From Date", "To Date", "" );
    }

    public abstract T getHasPriceRateSchedules();
}
