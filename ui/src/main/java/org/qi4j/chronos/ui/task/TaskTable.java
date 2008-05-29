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
package org.qi4j.chronos.ui.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskTable extends ActionTable<IModel, String>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( TaskTable.class );
    private AbstractSortableDataProvider<IModel, String> dataProvider;
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_FAIL = "deleteFailed";

    public TaskTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new SimpleDeleteAction<IModel>( getString( DELETE_ACTION ) )
            {
                public void performAction( List<IModel> tasks )
                {
                    handleDeleteAction( tasks );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
    }

    private void handleDeleteAction( List<IModel> tasks )
    {
        Account account = ChronosSession.get().getAccount();

        for( Project project : account.projects() )
        {
            if( project.tasks().containsAll( tasks ) )
            {
                project.tasks().removeAll( tasks );
            }
        }

        for( IModel iModel : tasks )
        {
            ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().remove( iModel.getObject() );
        }

        try
        {
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
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
                    return TaskTable.this.getSize();
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
                            protected Object load()
                            {
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, TaskEntityComposite.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> models = new ArrayList<IModel>();
                    for( final String taskId : TaskTable.this.dataList( first, count ) )
                    {
                        models.add(
                            new CompoundPropertyModel(
                                new LoadableDetachableModel()
                                {
                                    protected Object load()
                                    {
                                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( taskId, TaskEntityComposite.class );
                                    }
                                }
                            )
                        );
                    }
                    return models;
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( final Item item, final IModel iModel )
    {
        final Task task = (Task) iModel.getObject();
        final String id = ( (Identity) task ).identity().get();

        item.add(
            new SimpleLink( "title", task.title().get() )
            {
                public void linkClicked()
                {
                    handleViewDetail( id );
                }
            }
        );
        item.add( new Label( "createdDateLabel", DateUtil.formatDateTime( task.createdDate().get() ) ) );
        item.add( new Label( "createdByLabel", task.user().get().fullName().get() ) );

        SimpleLink editLink = new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                handleEdit( id, iModel );
            }
        };
        item.add( editLink );
    }

    private void handleViewDetail( final String taskId )
    {
        //TODO
//        TaskDetailPage detailPage = new TaskDetailPage( (BasePage) this.getPage(), taskId );
//        setResponsePage( detailPage );
    }

    private void handleEdit( final String id, final IModel iModel )
    {
        //TODO
//        TaskEditPage editPage = new TaskEditPage( (BasePage) this.getPage(), id )
//        {
//            public Task getTask()
//            {
//                return (Task) iModel.getObject();
//            }
//        };
//
//        setResponsePage( editPage );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Title", "Created Date", "Created by", "" );
    }

    public abstract int getSize();

    public abstract List<String> dataList( int first, int count );
}
