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
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
import org.qi4j.chronos.model.TaskAssignee;

public interface TaskService extends ProjectBasedService<TaskEntityComposite>
{
    List<TaskEntityComposite> getRecentTasks( AccountEntityComposite account );

    List<TaskEntityComposite> getRecentTasks( AccountEntityComposite account, FindFilter findFilter );

    int countRecentTasks( AccountEntityComposite account );

    List<TaskEntityComposite> getRecentTasks( StaffEntityComposite staff);

    List<TaskEntityComposite> getRecentTasks( StaffEntityComposite staff, FindFilter findFilter );

    int countRecentTasks( StaffEntityComposite staff );

    TaskEntityComposite getTaskByTaskAssignee( TaskAssigneeEntityComposite taskAssignee );
}
