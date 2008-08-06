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

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.injection.scope.Uses;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public class PriceRateScheduleListPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private IModel<HasPriceRateSchedules> hasPriceRateSchedules;

    public PriceRateScheduleListPage( @Uses IModel<HasPriceRateSchedules> hasPriceRateSchedules )
    {
        initComponents();

        this.hasPriceRateSchedules = hasPriceRateSchedules;
    }

    private void initComponents()
    {
        add(
            new Link( "newPriceRateScheduleLink" )
            {
                private static final long serialVersionUID = 1L;

                public void onClick()
                {
                    handleNewPriceRateSchedule();
                }
            }
        );
        add( new FeedbackPanel( "feedbackPanel" ) );

        PriceRateScheduleTable<HasPriceRateSchedules> table = new
            PriceRateScheduleTable<HasPriceRateSchedules>( "priceRateScheduleTable", hasPriceRateSchedules,
                                                           new PriceRateScheduleDataProvider( hasPriceRateSchedules ) );
        add( table );
    }

    private void handleNewPriceRateSchedule()
    {
/*
        PriceRateScheduleAddPage addPage = new PriceRateScheduleAddPage( this )
        {
            public HasPriceRateSchedules getHasPriceRateSchedules()
            {
                return PriceRateScheduleListPage.this.getAccount();
            }
        };

        setResponsePage( addPage );
*/
    }
}
