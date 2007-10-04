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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class PriceRateListView extends Panel
{
    private ListView listView;

    public PriceRateListView( String id, List<PriceRateDelegator> priceRateDelegators )
    {
        super( id );

        listView = new ListView( "priceRateListView", priceRateDelegators )
        {
            protected void populateItem( ListItem item )
            {
                PriceRateDelegator delegator = (PriceRateDelegator) item.getModelObject();

                item.add( new Label( "projectRole", delegator.getProjectRoleName() ) );
                item.add( new Label( "priceRateType", delegator.getPriceRateType().toString() ) );
                item.add( new Label( "amount", String.valueOf( delegator.getAmount() ) ) );
            }
        };

        add( listView );
    }

    public void resetPriceRateList( List<PriceRateDelegator> priceRateDelegators )
    {
        listView.setList( priceRateDelegators );
    }
}
