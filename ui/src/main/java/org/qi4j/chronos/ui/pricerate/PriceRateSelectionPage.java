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
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

public abstract class PriceRateSelectionPage extends LeftMenuNavPage
{
    private Page returnPage;

    public PriceRateSelectionPage( Page returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new PriceRateSelectionForm( "priceRateSelectionForm" ) );
    }

    private class PriceRateSelectionForm extends Form
    {
        private Button submitButton;
        private Button cancelButton;

        private SimpleDropDownChoice<PriceRateDelegator> priceRateChoice;

        public PriceRateSelectionForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            priceRateChoice = new SimpleDropDownChoice( "priceRateChoice", getPriceRateDelegator(), true );

            submitButton = new Button( "submitButton", new Model( "Select" ) )
            {
                public void onSubmit()
                {
                    handleSelect();
                }
            };

            cancelButton = new Button( "cancelButton", new Model( "Cancel" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            add( priceRateChoice );
            add( submitButton );
            add( cancelButton );
        }

        private void handleSelect()
        {
            PriceRateDelegator delegator = priceRateChoice.getChoice();

            PriceRateComposite priceRate = getServices().
                getPriceRateService().get( getPriceRateSchedule(),
                                           delegator.getProjectRoleName(), delegator.getPriceRateType(), delegator.getAmount() );

            handleSelectedPriceRate( priceRate );

            setResponsePage( returnPage );
        }

        private List<PriceRateDelegator> getPriceRateDelegator()
        {
            List<PriceRateDelegator> delegators = new ArrayList<PriceRateDelegator>();

            Iterator<PriceRateComposite> iter = getPriceRateSchedule().priceRateIterator();

            while( iter.hasNext() )
            {
                delegators.add( new PriceRateDelegator( iter.next() ) );
            }

            return delegators;
        }
    }

    public abstract void handleSelectedPriceRate( PriceRateComposite priceRate );

    public abstract PriceRateScheduleComposite getPriceRateSchedule();
}