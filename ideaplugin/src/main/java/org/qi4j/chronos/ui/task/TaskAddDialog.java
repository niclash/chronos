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

import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.util.ChronosUtil;

public class TaskAddDialog extends TaskAddEditDialog
{
    public TaskAddDialog()
    {
    }

    public User getTaskOwner()
    {
        return getChronosSetting().getStaff();
    }

    public String getOkButtonText()
    {
        return "Add";
    }

    public void handleOkClicked()
    {
        TaskService taskService = getServices().getTaskService();

        TaskEntityComposite task = taskService.newInstance( TaskEntityComposite.class );

        //set the owner of this task
        task.setUser( getChronosSetting().getStaff() );

        //set created date
        task.setCreatedDate( ChronosUtil.getCurrentDate() );

        //set values
        assignFieldValueToTask( task );

        ProjectEntityComposite project = getChronosSetting().getChronosProject();

        //add it to project
        project.addTask( task );

        //update project
        getServices().getProjectService().update( project );
    }

    public String getDialogTitle()
    {
        return "Add Task";
    }
}
