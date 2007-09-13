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

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.ui.base.BasePage;
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
            return new PriceRateScheduleTable( id )
            {
                public HasPriceRateSchedules getHasPriceRateSchedules()
                {
                    return PriceRateScheduleTab.this.getHasPriceRateSchedule();
                }
            };
        }

        public Link getNewLink( String id )
        {
            return new Link( id )
            {
                public void onClick()
                {
                    PriceRateScheduleAddPage<T> addPage = new PriceRateScheduleAddPage<T>( (BasePage) this.getPage() )
                    {
                        public T getHasPriceRateSchedule()
                        {
                            return PriceRateScheduleTab.this.getHasPriceRateSchedule();
                        }

                        public void addedPriceRateSchedule( T t )
                        {
                            PriceRateScheduleTab.this.addedPriceRateSchedule( t );
                        }
                    };
                    setResponsePage( addPage );
                }
            };
        }

        public String getNewLinkText()
        {
            return "New Price Rate Schedule";
        }
    }

    public abstract T getHasPriceRateSchedule();

    public abstract void addedPriceRateSchedule( T t );
}

