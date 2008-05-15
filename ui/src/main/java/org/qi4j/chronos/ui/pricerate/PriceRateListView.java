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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.PriceRateSchedule;

public class PriceRateListView extends Panel
{
    private ListView listView;

    public PriceRateListView( final String id, final IModel iModel )
    {
        super( id );

        final PriceRateSchedule priceRateSchedule = (PriceRateSchedule) iModel.getObject();
        final List<PriceRate> priceRates = new ArrayList<PriceRate>();
        for( PriceRate priceRate : priceRateSchedule.priceRates() )
        {
            priceRates.add( priceRate );
        }

        listView = new ListView( "priceRateListView", priceRates )
        {
            protected void populateItem( ListItem item )
            {
                final PriceRate priceRate = (PriceRate) item.getModelObject();

                item.add( new Label( "projectRole", priceRate.projectRole().get().name().get() ) );
                item.add( new Label( "priceRateType", priceRate.priceRateType().get().toString() ) );
                item.add( new Label( "amount", priceRate.displayValue().get() ) );
            }
        };

        add( listView );
    }

    public void resetPriceRateList( List<PriceRate> priceRates )
    {
        listView.setList( priceRates );
    }
}
