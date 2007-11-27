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
package org.qi4j.chronos.action.task;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import org.qi4j.chronos.action.AbstractAction;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.task.TaskListComponent;

public abstract class TaskBaseAction extends AbstractAction
{
    public final void actionPerformed( AnActionEvent e )
    {
        Object obj = e.getDataContext().getData( DataConstants.CONTEXT_COMPONENT );

        if( !( obj instanceof TaskListComponent ) )
        {
            throw new IllegalArgumentException( "Context component must implements interface TaskListComponent" );
        }

        TaskListComponent taskList = (TaskListComponent) obj;

        execute( taskList, e );
    }

    public TaskService getTaskService( AnActionEvent e )
    {
        return getServices( e ).getTaskService();
    }

    public abstract void execute( TaskListComponent taskList, AnActionEvent e );
}
