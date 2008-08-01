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

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.ui.address.AddressDetailPanel;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public class CustomerDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    public CustomerDetailPage( final Page returnPage, final IModel<Customer> customerModel )
    {
        add( new FeedbackPanel( "feedbackPanel" ) );

        ChronosCompoundPropertyModel<Customer> model = new ChronosCompoundPropertyModel<Customer>( customerModel.getObject() );

        setDefaultModel( model );

        TextField<String> nameField = new TextField<String>( "name", model.<String>bind( "name" ) );
        TextField<String> referenceField = new TextField<String>( "reference", model.<String>bind( "reference" ) );

        IModel<Address> addressModel = model.bind( "address" );
        AddressDetailPanel addressDetailPanel = new AddressDetailPanel( "addressDetailPanel", addressModel );

//            List<AbstractTab> tabs = new ArrayList<AbstractTab>();
//
//            tabs.add(
//                new ContactPersonTab()
//                {
//                    public Customer getCustomer()
//                    {
//                        return (Customer) CustomerDetailPage.this.getModelObject();
//                    }
//                }
//            );
//
//            tabs.add(
//                new PriceRateScheduleTab<Customer>( "Standard Price Rate Schedules" )
//                {
//                    public Account getAccount()
//                    {
//                        return CustomerDetailPage.this.getAccount();
//                    }
//
//                    public void addPriceRateSchedule( PriceRateSchedule priceRateSchedule )
//                    {
//                        handleAddPriceRateSchedule( priceRateSchedule );
//                    }
//
//                    public Customer getHasPriceRateSchedules()
//                    {
//                        return (Customer) CustomerDetailPage.this.getModelObject();
//                    }
//                }
//            );
//
//            ajaxTabbedPanel = new TabbedPanel( "ajaxTabbedPanel", tabs );

        Link goBackLink = new Link( "goBackLink" )
        {
            private static final long serialVersionUID = 1L;

            public void onClick()
            {
                setResponsePage( returnPage );
            }
        };

        add( nameField );
        add( referenceField );
        add( addressDetailPanel );
//            add( ajaxTabbedPanel );
        add( goBackLink );
    }

/*
    private void handleAddPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        // TODO kamil: complete this
        Customer customer = getCustomer();
        customer.priceRateSchedules().add( priceRateSchedule );
    }
*/


    protected Customer getCustomer()
    {
        return (Customer) getDefaultModelObject();
    }
}
