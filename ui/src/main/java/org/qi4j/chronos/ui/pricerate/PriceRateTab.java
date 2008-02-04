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

import org.apache.wicket.markup.html.basic.Label;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.ui.common.BorderPanel;
import org.qi4j.chronos.ui.common.tab.BaseTab;
import org.qi4j.chronos.ui.util.ListUtil;

public abstract class PriceRateTab extends BaseTab
{
    public PriceRateTab( String title )
    {
        super( title );
    }

    public BorderPanel getBorderPanel( String panelId )
    {
        return new PriceRatePanel( panelId );
    }

    private class PriceRatePanel extends BorderPanel
    {
        private PriceRateListView priceRateListView;
        private Label priceRateScheduleNameField;
        private Label priceRateScheduleCurrencyField;

        public PriceRatePanel( String id )
        {
            super( id );

            PriceRateScheduleComposite priceRateSchedule = getPriceRateSchedule();
            priceRateListView = new PriceRateListView( "priceRateListView", ListUtil.getPriceRateDelegator( priceRateSchedule ) );

            priceRateScheduleNameField = new Label( "priceRateScheduleNameLabel", priceRateSchedule.name().get() );
            priceRateScheduleCurrencyField = new Label( "priceRateScheduleCurrencyField", priceRateSchedule.currency().get().getCurrencyCode() );

            add( priceRateListView );
            add( priceRateScheduleNameField );
            add( priceRateScheduleCurrencyField );
        }
    }

    public abstract PriceRateScheduleComposite getPriceRateSchedule();
}
