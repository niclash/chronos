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
package org.qi4j.chronos.domain.model.common.task;

import java.util.Date;
import org.qi4j.api.common.Optional;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.chronos.domain.model.Entity;
import org.qi4j.chronos.domain.model.common.comment.Comment;
import org.qi4j.chronos.domain.model.common.comment.association.HasComments;
import org.qi4j.chronos.domain.model.common.description.HasDescription;
import org.qi4j.chronos.domain.model.common.task.assembly.TaskMixin;
import org.qi4j.chronos.domain.model.user.User;

@Mixins( TaskMixin.class )
public interface Task extends HasDescription, HasComments, Entity<Task>
{
    TaskId taskId();

    String title();

    TaskStatus status();

    void updateTaskStatus( TaskStatus newStatus, @Optional Comment comment );

    TaskPriority priority();

    void updatePriority( TaskPriority newPriority );

    Date createdDate();

    User createdBy();

    Query<WorkEntry> workEntries();

    User assignedTo();
}
