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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CustomerTable extends ActionTable<IModel, String>
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CustomerTable.class );
    private AbstractSortableDataProvider<IModel, String> provider;
    private static final String DISABLE_ACTION = "disableAction";
    private static final String DISABLE_SUCCESS = "disableSuccessful";
    private static final String DISABLE_FAIL = "disableFailed";
    private static final String ENABLE_ACTION = "enableAction";
    private static final String ENABLE_SUCCESS = "enableSuccessful";
    private static final String ENABLE_FAIL = "enableFailed";
    private static final String EDIT_LINK = "editLink";
    private static final String HEADER_NAME = "name";
    private static final String HEADER_REFERENCE = "formalReference";
    private static final String HEADER_ENABLED = "enabled";
    private static final String HEADER_LAST = "last";

    public CustomerTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new SimpleAction<IModel>( getString( DISABLE_ACTION ) )
            {
                public void performAction( List<IModel> customers )
                {
                    handleEnableAction( customers, false );
                    info( getString( DISABLE_SUCCESS ) );
                }
            }
        );

        addAction(
            new SimpleAction<IModel>( getString( ENABLE_ACTION ) )
            {
                public void performAction( List<IModel> customers )
                {
                    handleEnableAction( customers, true );
                    info( getString( ENABLE_SUCCESS ) );
                }
            }
        );
    }

    private void handleEnableAction( List<IModel> customers, boolean enable )
    {
        for( IModel iModel : customers )
        {
            Customer customer = (Customer) iModel.getObject();
            customer.isEnabled().set( enable );
        }

        try
        {
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            LOGGER.error( uowce.getLocalizedMessage(), uowce );
            error( getString( enable ? ENABLE_FAIL : DISABLE_FAIL ) );
        }
    }

    public AbstractSortableDataProvider<IModel, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new AbstractSortableDataProvider<IModel, String>()
            {
                public int getSize()
                {
                    return CustomerTable.this.getAccount().customers().size();
                }

                public String getId( IModel t )
                {
                    return ( (Identity) t.getObject() ).identity().get();
                }

                public IModel load( final String s )
                {
                    return new CompoundPropertyModel(
                        new LoadableDetachableModel()
                        {
                            public Object load()
                            {
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, CustomerEntityComposite.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> iModels = new ArrayList<IModel>();
                    for( final String customerId : CustomerTable.this.dataList( first, count ) )
                    {
                        iModels.add(
                            new CompoundPropertyModel(
                                new LoadableDetachableModel()
                                {
                                    public Object load()
                                    {
                                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( customerId, CustomerEntityComposite.class );
                                    }
                                }
                            )
                        );
                    }

                    return iModels;
                }
            };
        }

        return provider;
    }

    public abstract Account getAccount();

    public void populateItems( Item item, IModel iModel )
    {
        Customer customer = (Customer) iModel.getObject();
        final String customerId = ( (Identity) customer).identity().get();

        item.add(
            createDetailLink( HEADER_NAME, customer.name().get(), customer.isEnabled().get(), customerId )
        );
        item.add(
            createDetailLink( HEADER_REFERENCE, customer.reference().get(), customer.isEnabled().get(), customerId )
        );
        item.add(
            new SimpleCheckBox( HEADER_ENABLED, customer.isEnabled().get(), true )
        );
        item.add(
            new SimpleLink( EDIT_LINK, getString( EDIT_LINK ) )
            {
                public void linkClicked()
                {
                    CustomerEditPage editPage = new CustomerEditPage( this.getPage(), customerId );

                    setResponsePage( editPage );
                }
            }
        );
    }

    private SimpleLink createDetailLink( final String id,
                                         final String displayValue, final boolean enable, final String customerId )
    {
        return new SimpleLink( id, displayValue )
        {
            public void linkClicked()
            {
                CustomerDetailPage detailPage = new CustomerDetailPage( this.getPage(), customerId );
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
        return getTableHeaderList(
            HEADER_NAME,
            HEADER_REFERENCE,
            HEADER_ENABLED,
            HEADER_LAST
        );
    }

    public List<String> getTableHeaderList( String...headers )
    {
        List<String> result = new ArrayList<String>();
        for( String header : headers )
        {
            result.add( getString( "tableHeader." + header ) );
        }

        return Collections.unmodifiableList( result );
    }

    protected List<String> dataList( int first, int count )
    {
        List<String> customerIdList = new ArrayList<String>();
        for( Customer customer : getAccount().customers() )
        {
            customerIdList.add( ( (Identity) customer).identity().get() );
        }

        return customerIdList.subList( first, first + count );
    }
}
