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
package org.qi4j.chronos.ui.workentry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.composites.WorkEntryEntity;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WorkEntryTable extends ActionTable<IModel, String>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( WorkEntryTable.class );
    private AbstractSortableDataProvider<IModel, String> provider;
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_FAIL = "deleteFailed";

    public WorkEntryTable( String id )
    {
        super( id );

        initActions();
    }

    private void initActions()
    {
        addAction(
            new SimpleDeleteAction<IModel>( getString( DELETE_ACTION ) )
            {
                public void performAction( List<IModel> workEntries )
                {
                    handleDeleteAction( workEntries );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
    }

    private void handleDeleteAction( List<IModel> workEntries )
    {
        try
        {
            HasWorkEntries hasWorkEntries = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( WorkEntryTable.this.getHasWorkEntries() );
            for( IModel iModel : workEntries )
            {
                final WorkEntry workEntry = (WorkEntry) iModel.getObject();
                hasWorkEntries.workEntries().remove( workEntry );
                ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().remove( workEntry );
            }
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
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
                    final HasWorkEntries hasWorkEntries =
                        ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( WorkEntryTable.this.getHasWorkEntries() );
                    return hasWorkEntries.workEntries().size();
                }

                public String getId( IModel t )
                {
                    return ( (Identity) t.getObject() ).identity().get();
                }

                public IModel load( final String workEntryId )
                {
                    return new CompoundPropertyModel(
                        new LoadableDetachableModel()
                        {
                            protected Object load()
                            {
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( workEntryId, WorkEntryEntity.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> models = new ArrayList<IModel>();
                    for( final String workEntryId : WorkEntryTable.this.dataList( first, count ) )
                    {
                        models.add(
                            new CompoundPropertyModel(
                                new LoadableDetachableModel()
                                {
                                    protected Object load()
                                    {
                                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( workEntryId, WorkEntryEntity.class );
                                    }
                                }
                            )
                        );
                    }
                    return models;
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, IModel iModel )
    {
        final WorkEntry workEntry = (WorkEntry) iModel.getObject();
        final String workEntryId = ( (Identity) workEntry ).identity().get();

        item.add(
            new SimpleLink( "title", workEntry.title().get() )
            {
                public void linkClicked()
                {
                    WorkEntryDetailPage detailPage = new WorkEntryDetailPage( this.getPage(), workEntryId );
                    setResponsePage( detailPage );
                }
            }
        );
        item.add( new Label( "createdDate", DateUtil.formatDateTime( workEntry.createdDate().get() ) ) );
        item.add( new Label( "fromTime", DateUtil.formatDateTime( workEntry.startTime().get() ) ) );
        item.add( new Label( "toTime", DateUtil.formatDateTime( workEntry.endTime().get() ) ) );
        item.add(
            new SimpleLink( "editLink", "Edit" )
            {
                public void linkClicked()
                {
                    //TODO
//                    WorkEntryEditPage editPage = new WorkEntryEditPage( (BasePage) this.getPage(), workEntryId );
//                    setResponsePage( editPage );
                }
            }
        );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Title", "Created Date", "From Time", "To Time", "" );
    }

    public abstract HasWorkEntries getHasWorkEntries();

    public abstract List<String> dataList( int first, int count );
}
