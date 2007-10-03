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
package org.qi4j.chronos.ui.customer;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.address.AddressDetailPanel;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.contactperson.ContactPersonTab;
import org.qi4j.chronos.ui.pricerate.PriceRateScheduleTab;

public abstract class CustomerDetailPage extends LeftMenuNavPage
{
    private BasePage returnPage;

    public CustomerDetailPage( BasePage returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new CustomerDetailForm( "customerDetailForm" ) );
    }

    public abstract CustomerEntityComposite getCustomer();

    private class CustomerDetailForm extends Form
    {
        private Button submitButton;

        private SimpleTextField nameField;
        private SimpleTextField referenceField;

        private AddressDetailPanel addressDetailPanel;

        private TabbedPanel ajaxTabbedPanel;

        public CustomerDetailForm( String id )
        {
            super( id );

            CustomerEntityComposite customer = getCustomer();

            nameField = new SimpleTextField( "nameField", customer.getName(), true );
            referenceField = new SimpleTextField( "referenceField", customer.getReference(), true );

            addressDetailPanel = new AddressDetailPanel( "addressDetailPanel", customer.getAddress() );

            List<AbstractTab> tabs = new ArrayList<AbstractTab>();

            tabs.add( new ContactPersonTab()
            {
                public CustomerEntityComposite getCustomer()
                {
                    return CustomerDetailPage.this.getCustomer();
                }
            } );

            tabs.add( new PriceRateScheduleTab<CustomerEntityComposite>( "Standard Price Rate Schedules" )
            {
                public AccountEntityComposite getAccount()
                {
                    return CustomerDetailPage.this.getAccount();
                }

                public void addPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule )
                {
                    handleAddPriceRateSchedule( priceRateSchedule );
                }

                public CustomerEntityComposite getHasPriceRateSchedules()
                {
                    return getCustomer();
                }
            } );

            ajaxTabbedPanel = new TabbedPanel( "ajaxTabbedPanel", tabs );

            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            add( nameField );
            add( referenceField );
            add( addressDetailPanel );
            add( ajaxTabbedPanel );
            add( submitButton );
        }

        private void handleAddPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule )
        {
            CustomerEntityComposite customer = getCustomer();

            customer.addPriceRateSchedule( priceRateSchedule );

            CustomerService service = ChronosWebApp.getServices().getCustomerService();

            service.update( customer );
        }

    }
}

