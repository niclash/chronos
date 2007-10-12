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
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.util.DateUtil;

public abstract class TaskTable extends ActionTable<TaskEntityComposite, String>
{
    private TaskDataProvider dataProvider;

    public TaskTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<TaskEntityComposite, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new TaskDataProvider()
            {
                public int getSize()
                {
                    return TaskTable.this.getSize();
                }

                public List<TaskEntityComposite> dataList( int first, int count )
                {
                    return TaskTable.this.dataList( first, count );
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, TaskEntityComposite obj )
    {
        final String id = obj.getIdentity();

        item.add( new SimpleLink( "title", obj.getTitle() )
        {
            public void linkClicked()
            {
                handleViewDetail( id );
            }
        } );

        item.add( new Label( "createdDateLabel", DateUtil.formatDateTime( obj.getCreatedDate() ) ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                handleEdit( id );
            }
        } );
    }

    private void handleViewDetail( final String id )
    {
        TaskDetailPage detailPage = new TaskDetailPage( (BasePage) this.getPage() )
        {

            public TaskEntityComposite getTask()
            {
                return ChronosWebApp.getServices().getTaskService().get( id );
            }
        };

        setResponsePage( detailPage );
    }

    private void handleEdit( final String id )
    {
        TaskEditPage editPage = new TaskEditPage( (BasePage) this.getPage() )
        {
            public TaskEntityComposite getTaskMaster()
            {
                return ChronosWebApp.getServices().getTaskService().get( id );
            }
        };

        setResponsePage( editPage );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Title", "Created Date", "" );
    }

    public abstract int getSize();

    public abstract List<TaskEntityComposite> dataList( int first, int count );

}
