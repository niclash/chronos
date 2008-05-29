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
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PriceRateScheduleTable<T extends HasPriceRateSchedules> extends ActionTable<IModel, String>
{
    private AbstractSortableDataProvider<IModel, String> dataProvider;
    private final static Logger LOGGER = LoggerFactory.getLogger( PriceRateScheduleTable.class );
    private final static String DELETE_FAIL = "deleteFailed";
    private final static String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_ACTION = "deleteAction";

    public PriceRateScheduleTable( String id, IModel iModel )
    {
        super( id, iModel );
        setModel( iModel );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new SimpleDeleteAction<IModel>( getString( DELETE_ACTION ) )
            {
                public void performAction( List<IModel> priceRateSchedules )
                {
                    handleDelete( priceRateSchedules );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
    }

    /**
     * Delete PriceRateSchedule from HasPriceRateSchedules i.e. remove from Customer but not from Account
     * @param iModels
     */
    private void handleDelete( List<IModel> iModels )
    {
        try
        {
            final T hasPriceRateSchedule = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( getHasPriceRateSchedules() );
            for( IModel iModel : iModels )
            {
                final PriceRateSchedule priceRateSchedule = ( PriceRateSchedule) iModel.getObject();
                hasPriceRateSchedule.priceRateSchedules().remove( priceRateSchedule );
                ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().remove( priceRateSchedule );
            }
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
            error( getString( DELETE_FAIL, new Model( uowce ) ) );
        }
    }

    public AbstractSortableDataProvider<IModel, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new AbstractSortableDataProvider<IModel, String>()
            {
                public int getSize()
                {
                    return PriceRateScheduleTable.this.getHasPriceRateSchedules().priceRateSchedules().size();
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
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, PriceRateScheduleEntityComposite.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> priceRateSchedules = new ArrayList<IModel>();
                    for( final String priceRateScheduleId : PriceRateScheduleTable.this.dataList( first, count ) )
                    {
                        priceRateSchedules.add(
                            new CompoundPropertyModel(
                                new LoadableDetachableModel()
                                {
                                    public Object load()
                                    {
                                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find(
                                            priceRateScheduleId, PriceRateScheduleEntityComposite.class );
                                    }
                                }
                            )
                        );
                    }
                    return priceRateSchedules;
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, IModel iModel )
    {
        final PriceRateSchedule priceRateSchedule = (PriceRateSchedule) iModel.getObject();
        final String priceRateScheduleName = priceRateSchedule.name().get();
        final String priceRateScheduleId = ( (Identity) priceRateSchedule ).identity().get();

        item.add(
            new SimpleLink( "name", priceRateScheduleName )
            {
                public void linkClicked()
                {
                    PriceRateScheduleDetailPage detailPage =
                        new PriceRateScheduleDetailPage( (BasePage) this.getPage(), priceRateScheduleId );
                    setResponsePage( detailPage );
                }
            }
        );
        item.add( new Label( "currencyLabel", priceRateSchedule.currency().get().getCurrencyCode() ) );
        item.add(
            new SimpleLink( "editLink", "Edit" )
            {
                public void linkClicked()
                {
/*
                    PriceRateScheduleEditPage editPage =
                        new PriceRateScheduleEditPage( (BasePage) this.getPage(), priceRateScheduleId );
                    setResponsePage( editPage );
*/
                }
            }
        );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Currency", "" );
    }

    protected List<String> dataList( int first, int count )
    {
        List<String> priceRateSchedules = new ArrayList<String>();
        for( PriceRateSchedule priceRateSchedule : getHasPriceRateSchedules().priceRateSchedules() )
        {
            priceRateSchedules.add( ( (Identity) priceRateSchedule ).identity().get() );
        }
        return priceRateSchedules.subList( first, first + count );
    }

    public abstract T getHasPriceRateSchedules();

    public abstract Account getAccount();
}
