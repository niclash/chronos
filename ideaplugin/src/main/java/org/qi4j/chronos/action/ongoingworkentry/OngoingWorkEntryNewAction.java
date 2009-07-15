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
import org.qi4j.chronos.model.OngoingWorkEntry;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.service.OngoingWorkEntryService;
import org.qi4j.chronos.task.TaskListComponent;
import org.qi4j.chronos.util.ChronosUtil;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.util.UiUtil;
import org.qi4j.entity.association.ManyAssociation;

public class OngoingWorkEntryNewAction extends TaskBaseAction
{
    public void execute( final TaskListComponent taskList, AnActionEvent e )
    {
        if( !UiUtil.showConfirmationDialog( "Confirmation", "Are you sure want to start work?" ) )
        {
            return;
        }

        Task task = taskList.getSelectedTask();

        OngoingWorkEntryService service = getServices( e ).getOngoingWorkEntryService();

        OngoingWorkEntry ongoingWorkEntry = service.newInstance( OngoingWorkEntry.class );

        //set created date and projectassignee
        ongoingWorkEntry.createdDate().set( ChronosUtil.getCurrentDate() );
        ongoingWorkEntry.projectAssignee().set( getProjectAssignee( e ) );

        //add it to task
        ManyAssociation<OngoingWorkEntry> onGoingWorkEntries = task.onGoingWorkEntries();
        onGoingWorkEntries.add( ongoingWorkEntry );

        //update task
        getServices( e ).getTaskService().update( task );

        UiUtil.showMsgDialog( "Work Started.", "Work is started. Started Date is " +
                                               DateUtil.formatDateTime( ongoingWorkEntry.createdDate().get() ) );
    }
}
