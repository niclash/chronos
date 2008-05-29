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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.associations.HasCustomers;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.chronos.ui.wicket.model.ChronosEntityModel;
import org.qi4j.entity.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CustomerTable extends ActionTable<IModel<Customer>, String>
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CustomerTable.class );
    private AbstractSortableDataProvider<IModel<Customer>, String> provider;

    public CustomerTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new SimpleAction<IModel<Customer>>( "Disable" )
            {
                public void performAction( List<IModel<Customer>> customers )
                {
                    handleEnableAction( customers, false );
                    info( "The selected customer(s) is disabled successfully." );
                }
            }
        );

        addAction(
            new SimpleAction<IModel<Customer>>( "Enable" )
            {
                public void performAction( List<IModel<Customer>> customers )
                {
                    handleEnableAction( customers, true );
                    info( "The selected customer(s) is disabled successfully." );
                }
            }
        );
    }

    private void handleEnableAction( List<IModel<Customer>> customers, boolean enable )
    {
        for( IModel<Customer> iModel : customers )
        {
            Customer customer = iModel.getObject();
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

    public AbstractSortableDataProvider<IModel<Customer>, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new AbstractSortableDataProvider<IModel<Customer>, String>()
            {
                public int getSize()
                {
                    return CustomerTable.this.getHasCustomersModel().getObject().customers().size();
                }

                public String getId( IModel<Customer> customerModel )
                {
                    return ( (Identity) customerModel.getObject() ).identity().get();
                }

                public IModel<Customer> load( final String identityId )
                {
                    return new ChronosCompoundPropertyModel<Customer>(
                                ChronosUnitOfWorkManager.get().getCurrentUnitOfWork()
                                    .find( identityId, CustomerEntityComposite.class )
                    );
                }

                public List<IModel<Customer>> dataList( int first, int count )
                {
                    List<IModel<Customer>> customerModels = new ArrayList<IModel<Customer>>();
                    for( Customer customer : CustomerTable.this.dataList( first, count ) )
                    {
                        customerModels.add( new ChronosCompoundPropertyModel<Customer>( customer ) );
                    }
                    return customerModels;
                }
            };
        }

        return provider;
    }

    public abstract Account getAccount();

    public void populateItems( Item item, IModel<Customer> iModel )
    {
        Customer customer = iModel.getObject();
        final ChronosEntityModel<Customer> customerModel = new ChronosEntityModel<Customer>( customer );
        item.add( createDetailLink( "name", customer.name().get(), customer.isEnabled().get(), customerModel ) );
        item.add( createDetailLink( "reference", customer.reference().get(),
                                    customer.isEnabled().get(), customerModel ) );
        item.add( new SimpleCheckBox( "enabled", customer.isEnabled().get(), true ) );
        item.add(
            new SimpleLink( "editLink", "Edit" )
            {
                public void linkClicked()
                {
                    CustomerEditPage editPage = new CustomerEditPage( this.getPage(), customerModel );

                    setResponsePage( editPage );
                }
            }
        );
    }

    private SimpleLink createDetailLink( final String id, final String displayValue,
                                         final boolean enable, final IModel<Customer> customerIModel )
    {
        return new SimpleLink( id, displayValue )
        {
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

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Reference", "Enabled", "Edit" );
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

    protected List<Customer> dataList( int first, int count )
    {
        List<Customer> customers = new ArrayList<Customer>();
        for( Customer customer : getAccount().customers() )
        {
            customers.add( customer );
        }

        return customers.subList( first, first + count );
    }

    public abstract IModel<HasCustomers> getHasCustomersModel();
}
