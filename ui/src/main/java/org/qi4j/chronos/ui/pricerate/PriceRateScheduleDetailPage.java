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

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.util.DateUtil;

public abstract class PriceRateScheduleDetailPage extends LeftMenuNavPage
{
    private BasePage returnBase;

    public PriceRateScheduleDetailPage( BasePage returnBase )
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
        private SimpleTextField fromDateField;
        private SimpleTextField toDateField;

        private Button submitButton;

        public PriceRateScheduleDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            PriceRateSchedule priceRateSchedule = getPriceRateSchedule();

            nameField = new SimpleTextField( "nameField", priceRateSchedule.getName(), true );
            fromDateField = new SimpleTextField( "fromDateField", DateUtil.format( "dd MMM yyyy", priceRateSchedule.getStartTime() ), true );
            toDateField = new SimpleTextField( "toDateField", DateUtil.format( "dd MMM yyyy", priceRateSchedule.getEndTime() ), true );

            submitButton = new Button( "submitButton", new Model( "Return" ) );

            add( nameField );
            add( fromDateField );
            add( toDateField );

            add( submitButton );
        }

        protected void delegateSubmit( IFormSubmittingComponent submittingButton )
        {
            if( submittingButton == submitButton )
            {
                setResponsePage( returnBase );
            }
            else
            {
                throw new IllegalArgumentException( submittingButton + " not handled yet" );
            }
        }
    }

    public abstract PriceRateSchedule getPriceRateSchedule();
}
