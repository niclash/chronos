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
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.associations.HasTasks;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class TaskDataProvider extends AbstractSortableDataProvider<Task>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasTasks> hasTasks;

    public TaskDataProvider( IModel<? extends HasTasks> hasTasks )
    {
        this.hasTasks = hasTasks;
    }

    public IModel<Task> load( String id )
    {
        Task task = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, Task.class );

        return new ChronosDetachableModel<Task>( task );
    }

    public List<Task> dataList( int first, int count )
    {
        //TODO
        List<Task> tasks = new ArrayList<Task>( hasTasks.getObject().tasks() );

        return tasks.subList( first, count );
    }

    public int size()
    {
        return hasTasks.getObject().tasks().size();
    }
}
