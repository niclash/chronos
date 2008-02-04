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
package org.qi4j.chronos.task;

import com.intellij.openapi.project.Project;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.TaskEntityComposite;

public abstract class TaskEditDialog extends TaskAddEditDialog
{
    public TaskEditDialog( Project project )
    {
        super( project );
        assignTaskToFieldValue( getTask() );
    }

    public User getCreatedBy()
    {
        return getTask().user().get();
    }

    public String getOkButtonText()
    {
        return "Save";
    }

    public void handleOkClicked()
    {
        TaskEntityComposite task = getTask();

        //set values
        assignFieldValueToTask( task );

        //TODO bp. Disallow changing of taskStatus from opened to WontFix/closed
        //TODO if the task has ongoingWorkEntry
        //update task
        getServices().getTaskService().update( task );
    }

    public String getDialogTitle()
    {
        return "Edit Task";
    }

    public abstract TaskEntityComposite getTask();
}
