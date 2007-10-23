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
import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.service.PriceRateScheduleService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.util.ListUtil;

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
        private SimpleDropDownChoice<String> priceRateScheduleChoice;

        private PriceRateListView priceRateListView;

        private SimpleTextField currencyField;

        private Button submitButton;
        private Button cancelButton;

        public PriceRateScheduleSelectionForm( String id )
        {
            super( id );

            intiComponents();
        }

        private void intiComponents()
        {
            List<String> nameList = getPriceRateScheduleNameList();

            priceRateScheduleChoice = new SimpleDropDownChoice<String>( "priceRateScheduleChoice", nameList, true )
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

            PriceRateScheduleComposite priceRateSchedule = getSelectedPriceRateSchedule();

            List<PriceRateDelegator> delegators = ListUtil.getPriceRateDelegator( priceRateSchedule );

            currencyField = new SimpleTextField( "currencyField", priceRateSchedule.getCurrency().getCurrencyCode() );

            priceRateListView = new PriceRateListView( "priceRateListView", delegators );

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
            PriceRateScheduleComposite priceRateSchedule = getSelectedPriceRateSchedule();

            handleSelectedPriceRateSchedule( priceRateSchedule );

            setResponsePage( basePage );
        }

        private PriceRateScheduleComposite getSelectedPriceRateSchedule()
        {
            String name = priceRateScheduleChoice.getChoice();

            return getPriceRateScheduleService().get( getAccount(), name );
        }

        private void handlePriceRateSheduleChanged()
        {
            PriceRateScheduleComposite priceRateSchedule = getSelectedPriceRateSchedule();

            List<PriceRateDelegator> delegators = ListUtil.getPriceRateDelegator( priceRateSchedule );

            priceRateListView.resetPriceRateList( delegators );
        }
    }

    private List<String> getPriceRateScheduleNameList()
    {
        List<String> nameList = new ArrayList<String>();
        List<PriceRateScheduleComposite> list = getAvailablePriceRateScheduleList();

        for( PriceRateScheduleComposite priceRateSchedule : list )
        {
            nameList.add( priceRateSchedule.getName() );
        }

        return nameList;
    }

    private PriceRateScheduleService getPriceRateScheduleService()
    {
        return ChronosWebApp.getServices().getPriceRateScheduleService();
    }

    private List<PriceRateScheduleComposite> getAvailablePriceRateScheduleList()
    {
        return getPriceRateScheduleService().findAll( getAccount() );
    }

    public abstract void handleSelectedPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule );

}
