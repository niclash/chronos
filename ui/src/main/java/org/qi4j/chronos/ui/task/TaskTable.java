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

import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.associations.HasTasks;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TaskTable extends ActionTable<Task>
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger( TaskTable.class );

    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_FAIL = "deleteFailed";

    private final static String[] COLUMN_NAMES = { "Title", "Created Date", "Created by", "" };
    private static final String WICKET_ID_CREATED_DATE_LABEL = "createdDateLabel";
    private static final String WICKET_ID_CREATED_BY_LABEL = "createdByLabel";
    private static final String WICKET_ID_EDIT_LINK = "editLink";
    private static final String WICKET_ID_TITLE = "title";

    public TaskTable( String id, IModel<? extends HasTasks> hasTasks, TaskDataProvider dataProvider )
    {
        super( id, hasTasks, dataProvider, COLUMN_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction( new DeleteAction<Task>( getString( DELETE_ACTION ) )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Task> tasks )
            {
                handleDeleteAction( tasks );
                info( getString( DELETE_SUCCESS ) );
            }
        }
        );
    }

    private void handleDeleteAction( List<Task> tasks )
    {
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
            ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().remove( task );
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

    public void populateItems( Item<Task> item )
    {
        final Task task = item.getModelObject();
        final String id = task.identity().get();

        item.add( new SimpleLink( WICKET_ID_TITLE, task.title().get() )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                handleViewDetail( id );
            }
        }
        );
        item.add( new Label( WICKET_ID_CREATED_DATE_LABEL, DateUtil.formatDateTime( task.createdDate().get() ) ) );
        item.add( new Label( WICKET_ID_CREATED_BY_LABEL, task.user().get().fullName().get() ) );

        SimpleLink editLink = new SimpleLink( WICKET_ID_EDIT_LINK, "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                handleEdit( task );
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

    private void handleEdit( final Task task )
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
}
