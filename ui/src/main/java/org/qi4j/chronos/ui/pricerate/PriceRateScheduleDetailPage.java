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

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.util.ListUtil;

public abstract class PriceRateScheduleDetailPage extends LeftMenuNavPage
{
    private Page returnBase;

    public PriceRateScheduleDetailPage( Page returnBase )
    {
        this.returnBase = returnBase;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new PriceRateScheduleDetailForm( "priceRateScheduleDetailForm" ) );
    }

    private class PriceRateScheduleDetailForm extends Form
    {
        private SimpleTextField nameField;
        private SimpleTextField currencyField;

        private Button submitButton;

        private PriceRateListView priceRateListView;

        public PriceRateScheduleDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            PriceRateSchedule priceRateSchedule = getPriceRateSchedule();

            priceRateListView = new PriceRateListView( "priceRateListView", ListUtil.getPriceRateDelegator( priceRateSchedule ) );
            currencyField = new SimpleTextField( "currencyField", priceRateSchedule.getCurrency().getCurrencyCode() );

            nameField = new SimpleTextField( "nameField", priceRateSchedule.getName(), true );

            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnBase );
                }
            };

            add( currencyField );
            add( nameField );
            add( submitButton );
            add( priceRateListView );
        }
    }

    public abstract PriceRateSchedule getPriceRateSchedule();
}
