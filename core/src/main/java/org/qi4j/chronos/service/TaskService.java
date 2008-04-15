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
package org.qi4j.chronos.service;

import java.util.List;
import org.qi4j.chronos.model.TaskStatus;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.TaskStatusEnum;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;

public interface TaskService extends ProjectBasedService<TaskEntityComposite>
{
    List<Task> getRecentTasks( Account account );

    List<Task> getRecentTasks( Account account, FindFilter findFilter );

    int countRecentTasks( Account account );

    List<Task> getRecentTasks( Staff staff );

    List<Task> getRecentTasks( Staff staff, FindFilter findFilter );

    int countRecentTasks( Staff staff );

    List<Task> findTask( Project project, TaskStatusEnum taskStatus );

//    Task getTaskByTaskAssignee( TaskAssignee taskAssignee );
}
