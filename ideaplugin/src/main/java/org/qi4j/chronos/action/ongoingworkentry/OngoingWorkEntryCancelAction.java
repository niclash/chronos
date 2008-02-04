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
package org.qi4j.chronos.action.ongoingworkentry;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.qi4j.chronos.action.task.TaskBaseAction;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.OngoingWorkEntryService;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.task.TaskListComponent;
import org.qi4j.chronos.util.UiUtil;

public class OngoingWorkEntryCancelAction extends TaskBaseAction
{
    public void execute( final TaskListComponent taskList, AnActionEvent e )
    {
        if( !UiUtil.showConfirmationDialog( "Confirmation", "Are you sure want to cancel the workentry?" ) )
        {
            return;
        }

        TaskEntityComposite task = taskList.getSelectedTask();

        Services services = getServices( e );

        OngoingWorkEntryService service = getServices( e ).getOngoingWorkEntryService();

        //get the ongoingWorkEntry
        final OngoingWorkEntryEntityComposite ongoingWorkEntry = service.getOngoingWorkEntry( task, getProjectAssignee( e ).staff().get() );

        //remove it from task
        task.onGoingWorkEntries().remove( ongoingWorkEntry );

        //update task
        services.getTaskService().update( taskList.getSelectedTask() );

        UiUtil.showMsgDialog( "Work cancelled!", "Work is cancelled." );

        //update taskTree
        taskList.refreshList();
    }
}
