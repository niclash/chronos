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

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;

public abstract class PriceRateScheduleTab<T extends HasPriceRateSchedules> extends NewLinkTab
{
    public PriceRateScheduleTab( String title )
    {
        super( title );
    }

    public NewLinkPanel getNewLinkPanel( String panelId )
    {
        return new PriceRateScheduleNewLinkPanel( panelId );
    }

    private class PriceRateScheduleNewLinkPanel extends NewLinkPanel
    {
        public PriceRateScheduleNewLinkPanel( String id )
        {
            super( id );
        }

        public Panel getContent( String id )
        {
            final IModel iModel =
                new CompoundPropertyModel(
                    new LoadableDetachableModel()
                    {
                        public Object load()
                        {
                            return PriceRateScheduleTab.this.getAccount();
                        }
                    }
                );

            return new PriceRateScheduleTable( id, iModel )
            {
                public HasPriceRateSchedules getHasPriceRateSchedules()
                {
                    return PriceRateScheduleTab.this.getHasPriceRateSchedules();
                }

                public Account getAccount()
                {
                    return PriceRateScheduleTab.this.getAccount();
                }
            };
        }

        public void newLinkOnClick()
        {
            PriceRateScheduleAddPage addPage =
                new PriceRateScheduleAddPage( PriceRateScheduleNewLinkPanel.this.getPage() )
            {
                public void addPriceRateSchedule( PriceRateSchedule priceRateSchedule )
                {
                    PriceRateScheduleTab.this.addPriceRateSchedule( priceRateSchedule );
                }

                public T getHasPriceRateSchedules()
                {
                    return PriceRateScheduleTab.this.getHasPriceRateSchedules();
                }
            };
            setResponsePage( addPage );
        }

        public String getNewLinkText()
        {
            return "New Price Rate Schedule";
        }
    }

    public abstract Account getAccount();

    public abstract void addPriceRateSchedule( PriceRateSchedule priceRateSchedule );

    public abstract T getHasPriceRateSchedules();
}
