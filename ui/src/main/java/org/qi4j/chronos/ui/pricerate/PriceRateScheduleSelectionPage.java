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
import java.io.Serializable;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.CompoundPropertyModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.common.NameChoiceRenderer;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class PriceRateScheduleSelectionPage extends LeftMenuNavPage
{
    private Page basePage;

    public PriceRateScheduleSelectionPage( Page basePage )
    {
        this.basePage = basePage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new PriceRateScheduleSelectionForm( "priceRateScheduleSelectionForm" ) );
    }

    private class PriceRateScheduleSelectionForm extends Form
    {
        private DropDownChoice priceRateScheduleChoice;
        private PriceRateListView priceRateListView;
        private SimpleTextField currencyField;
        private final IChoiceRenderer nameChoiceRenderer = new NameChoiceRenderer();

        private Button submitButton;
        private Button cancelButton;

        public PriceRateScheduleSelectionForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            priceRateScheduleChoice =
                new DropDownChoice( "priceRateScheduleChoice", getAvailablePriceRateScheduleList(), nameChoiceRenderer )
                {
                    protected boolean wantOnSelectionChangedNotifications()
                    {
                        return true;
                    }

                    protected void onSelectionChanged( Object newSelection )
                    {
                        handlePriceRateSheduleChanged();
                    }
                };
            priceRateScheduleChoice.setModel(
                new Model( (Serializable) getAvailablePriceRateScheduleList().get( 0 ) ) );
            PriceRateSchedule priceRateSchedule = (PriceRateSchedule) priceRateScheduleChoice.getModelObject();
            currencyField =
                new SimpleTextField( "currencyField", priceRateSchedule.currency().get().getCurrencyCode() );
            priceRateListView =
                new PriceRateListView( "priceRateListView", new CompoundPropertyModel( priceRateSchedule ) );
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
                    setResponsePage( basePage );
                }
            };

            add( currencyField );
            add( priceRateScheduleChoice );
            add( priceRateListView );
            add( submitButton );
            add( cancelButton );
        }

        private void handleSelect()
        {
            PriceRateSchedule priceRateSchedule = (PriceRateSchedule) priceRateScheduleChoice.getModelObject();

            handleSelectedPriceRateSchedule( priceRateSchedule );

            setResponsePage( basePage );
        }

        private void handlePriceRateSheduleChanged()
        {
            PriceRateSchedule priceRateSchedule = (PriceRateSchedule) priceRateScheduleChoice.getModelObject();

            priceRateListView.resetPriceRateList( new ArrayList( priceRateSchedule.priceRates() ) );
        }
    }

    private List<PriceRateSchedule> getAvailablePriceRateScheduleList()
    {
        return new ArrayList<PriceRateSchedule>( getAccount().priceRateSchedules() );
    }

    public abstract void handleSelectedPriceRateSchedule( PriceRateSchedule priceRateSchedule );
}
