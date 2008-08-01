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
import java.util.Collections;
import java.util.List;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.associations.HasCustomers;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DefaultAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosEntityModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CustomerTable extends ActionTable<Customer>
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( CustomerTable.class );

    private final static String[] COLUMN_NAMES = { "Name", "Reference", "Enabled", "Edit" };

    public CustomerTable( String id, IModel<HasCustomers> hasCustomers, CustomerDataProvider dataProvider )
    {
        super( id, hasCustomers, dataProvider, COLUMN_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new DefaultAction<Customer>( "Disable" )
            {
                private static final long serialVersionUID = 1L;

                public void performAction( List<Customer> customers )
                {
                    handleEnableAction( customers, false );
                    info( "The selected customer(s) is disabled successfully." );
                }
            }
        );

        addAction(
            new DefaultAction<Customer>( "Enable" )
            {
                private static final long serialVersionUID = 1L;

                public void performAction( List<Customer> customers )
                {
                    handleEnableAction( customers, true );
                    info( "The selected customer(s) is disabled successfully." );
                }
            }
        );
    }

    private void handleEnableAction( List<Customer> customers, boolean enable )
    {
        for( Customer customer : customers )
        {
            customer.isEnabled().set( enable );
        }

        try
        {
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( Exception ex )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            LOGGER.error( ex.getMessage(), ex );
            error( "Unable to " + ( enable ? "enable" : "disable" ) + " selected customer(s)." );
        }
    }

    private SimpleLink createDetailLink( final String id, final String displayValue,
                                         final boolean enable, final IModel<Customer> customerIModel )
    {
        return new SimpleLink( id, displayValue )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                CustomerDetailPage detailPage = new CustomerDetailPage( this.getPage(), customerIModel );
                setResponsePage( detailPage );
            }

            protected void authorizingLink( Link link )
            {
                link.setEnabled( enable );
            }
        };
    }

    public List<String> getTableHeaderList( String... headers )
    {
        List<String> result = new ArrayList<String>();
        for( String header : headers )
        {
            result.add( getString( "tableHeader." + header ) );
        }

        return Collections.unmodifiableList( result );
    }

    public void populateItems( Item<Customer> item )
    {
        Customer customer = item.getModelObject();

        final ChronosEntityModel<Customer> customerModel = new ChronosEntityModel<Customer>( customer );
        item.add( createDetailLink( "name", customer.name().get(), customer.isEnabled().get(), customerModel ) );
        item.add( createDetailLink( "reference", customer.reference().get(),
                                    customer.isEnabled().get(), customerModel ) );
        item.add( new CheckBox( "enabled", new Model<Boolean>( customer.isEnabled().get() ) ) );
        item.add( new SimpleLink( "editLink", "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                CustomerEditPage editPage = new CustomerEditPage( this.getPage(), customerModel );

                setResponsePage( editPage );
            }
        }
        );
    }
}
