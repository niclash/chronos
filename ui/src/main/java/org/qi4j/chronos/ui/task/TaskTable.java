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

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.Session;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;

public abstract class TaskTable extends ActionTable<Task, String>
{
    private TaskDataProvider dataProvider;

    public TaskTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<Task>( "Delete" )
        {
            public void performAction( List<Task> tasks )
            {
                // TODO kamil: migrate
                UnitOfWork unitOfWork = null == getUnitOfWorkFactory().currentUnitOfWork() ?
                                        getUnitOfWorkFactory().newUnitOfWork() :
                                        getUnitOfWorkFactory().currentUnitOfWork();

                Account account = ChronosSession.get().getAccount();
                for( Project project : account.projects() )
                {
                    if( project.tasks().containsAll( tasks ) )
                    {
                        project.tasks().removeAll( tasks );
                    }
                }

                for( Task task : tasks )
                {
                    unitOfWork.remove( task );
                }

                try
                {
                    unitOfWork.complete();
                }
                catch( UnitOfWorkCompletionException uowce )
                {
                    // TODO kamil: use LOGGER
                    System.err.println( uowce.getLocalizedMessage() );
                    uowce.printStackTrace();
                    error( "Unable to delete selected task(s)!!!" );
                }
//                getTaskService().delete( tasks );

                info( "Selected task(s) are deleted." );
            }
        } );
    }

    public AbstractSortableDataProvider<Task, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new TaskDataProvider()
            {
                public int getSize()
                {
                    return TaskTable.this.getSize();
                }

                public List<Task> dataList( int first, int count )
                {
                    return TaskTable.this.dataList( first, count );
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, Task obj )
    {
        final String id = ( (Identity) obj).identity().get();

        item.add( new SimpleLink( "title", obj.title().get() )
        {
            public void linkClicked()
            {
                handleViewDetail( id );
            }
        } );

        item.add( new Label( "createdDateLabel", DateUtil.formatDateTime( obj.createdDate().get() ) ) );

        item.add( new Label( "createdByLabel", obj.user().get().fullName().get() ) );

        SimpleLink editLink = new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                handleEdit( id );
            }
        };

        item.add( editLink );
    }

    private void handleViewDetail( final String id )
    {
        TaskDetailPage detailPage = new TaskDetailPage( (BasePage) this.getPage() )
        {
            public Task getTask()
            {
                // TODO kamil: migrate
//                return getTaskService().get( id );
                for( Task task : dataList( 0, getSize() ) )
                {
                    if( id.equals( ( (Identity) task).identity().get() ) )
                    {
                        return task;
                    }
                }

                return null;
            }
        };

        setResponsePage( detailPage );
    }

    private void handleEdit( final String id )
    {
        TaskEditPage editPage = new TaskEditPage( (BasePage) this.getPage() )
        {
            public Task getTask()
            {
                // TODO kamil: migrate
//                return getTaskService().get( id );
                for( Task task : dataList( 0, getSize() ) )
                {
                    if( id.equals( ( (Identity) task).identity().get() ) )
                    {
                        return task;
                    }
                }

                return null;
                
            }
        };

        setResponsePage( editPage );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Title", "Created Date", "Created by", "" );
    }

    private UnitOfWorkFactory getUnitOfWorkFactory()
    {
        return ( ( ChronosSession ) Session.get() ).getUnitOfWorkFactory();
    }

    public abstract int getSize();

    public abstract List<Task> dataList( int first, int count );

}
