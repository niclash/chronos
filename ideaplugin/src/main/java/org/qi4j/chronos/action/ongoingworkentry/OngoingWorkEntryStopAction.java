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
import java.util.Date;
import org.qi4j.chronos.action.task.TaskBaseAction;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.OngoingWorkEntryService;
import org.qi4j.chronos.task.TaskListComponent;
import org.qi4j.chronos.util.UiUtil;
import org.qi4j.chronos.workentry.WorkEntryAddDialog;

public class OngoingWorkEntryStopAction extends TaskBaseAction
{
    public void execute( final TaskListComponent taskList, final AnActionEvent e )
    {
        final TaskEntityComposite task = taskList.getSelectedTask();

        OngoingWorkEntryService service = getServices( e ).getOngoingWorkEntryService();

        final OngoingWorkEntryEntityComposite ongoingWorkEntry = service.getOngoingWorkEntry( task, getProjectAssignee( e ).getStaff() );

        WorkEntryAddDialog addDialog = new WorkEntryAddDialog()
        {
            public void addingWorkEntry( WorkEntryEntityComposite workEntry )
            {
                //add workentry to task
                task.addWorkEntry( workEntry );

                //remove the ongoingWorkEntry
                task.removeOngoingWorkEntry( ongoingWorkEntry );

                //update task
                getServices().getTaskService().update( task );

                UiUtil.showMsgDialog( "Work stopped.", "New workentry is added successfully." );

                //update tasktree
                taskList.refreshList();
            }

            public Date getStartedDate()
            {
                return ongoingWorkEntry.getCreatedDate();
            }
        };

        addDialog.show();
    }
}
