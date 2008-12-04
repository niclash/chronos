/*  Copyright 2008 Edward Yakop.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
* implied.
*
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.qi4j.chronos.domain.model.common.task;

import java.util.Date;
import org.qi4j.chronos.domain.model.WorkEntry;
import org.qi4j.chronos.domain.model.common.comment.Comment;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.composite.Optional;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.injection.scope.This;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public abstract class TaskMixin
    implements Task
{
    private final TaskId taskId;
    @This private TaskState state;

    @Structure private UnitOfWorkFactory uowf;

    public TaskMixin( @This Identity identity )
    {
        taskId = new TaskId( identity.identity().get() );
    }

    public final TaskId taskId()
    {
        return taskId;
    }

    public final String title()
    {
        return state.title().get();
    }

    public final TaskStatus status()
    {
        return state.status().get();
    }

    public final void updateTaskStatus( TaskStatus newStatus, @Optional Comment comment )
    {
        state.status().set( newStatus );
        state.comments().add( comment );
    }

    public final TaskPriority priority()
    {
        return state.priority().get();
    }

    public final void updatePriority( TaskPriority newPriority )
    {
        if( status().equals( TaskStatus.open ) )
        {
            state.priority().set( newPriority );
        }
    }

    public final Date createdDate()
    {
        return state.createdDate().get();
    }

    public final User createdBy()
    {
        return state.createdBy().get();
    }

    public final Query<Comment> comments()
    {
        UnitOfWork uow = uowf.currentUnitOfWork();
        QueryBuilderFactory qbf = uow.queryBuilderFactory();
        QueryBuilder<Comment> builder = qbf.newQueryBuilder( Comment.class );
        return builder.newQuery( state.comments() );
    }

    public final Query<WorkEntry> workEntries()
    {
        UnitOfWork uow = uowf.currentUnitOfWork();
        QueryBuilderFactory qbf = uow.queryBuilderFactory();
        QueryBuilder<WorkEntry> builder = qbf.newQueryBuilder( WorkEntry.class );
        return builder.newQuery( state.workEntries() );
    }

    public final User assignedTo()
    {
        return state.assignedTo().get();
    }

    public final boolean sameIdentityAs( Task other )
    {
        return other != null && taskId.sameValueAs( other.taskId() );
    }
}