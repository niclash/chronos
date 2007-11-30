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
package org.qi4j.chronos.task.tree;

import com.intellij.openapi.project.Project;
import java.util.List;
import org.qi4j.chronos.ChronosApp;
import org.qi4j.chronos.ChronosProjectComponent;
import org.qi4j.chronos.common.AbstractTreeNode;
import org.qi4j.chronos.model.TaskStatus;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.OngoingWorkEntryService;
import org.qi4j.chronos.service.TaskService;

public class TaskRootTreeNode extends AbstractTreeNode
{
    private TaskOngoingGroupTreeNode taskOngoingGroupTreeNode;
    private TaskOpenedGroupTreeNode taskOpenedGroupTreeNode;

    private Project project;

    public TaskRootTreeNode( Project project, TaskService taskService )
    {
        this.project = project;

        initGroupTreeNodes();
        loadTaskTreeNode( taskService );
    }

    private void loadTaskTreeNode( TaskService taskService )
    {
        List<TaskEntityComposite> tasks = taskService.findTask( getChronosApp().getChronosProject(), TaskStatus.OPEN );

        for( TaskEntityComposite task : tasks )
        {
            addTaskTreeNode( task );
        }
    }

    private ChronosApp getChronosApp()
    {
        return ChronosProjectComponent.getInstance( project ).getChronosApp();
    }

    private void initGroupTreeNodes()
    {
        taskOngoingGroupTreeNode = new TaskOngoingGroupTreeNode();
        taskOpenedGroupTreeNode = new TaskOpenedGroupTreeNode();

        this.add( taskOngoingGroupTreeNode );
        this.add( taskOpenedGroupTreeNode );
    }

    public void addTaskTreeNode( TaskEntityComposite task )
    {
        if( task.getTaskStatus() != TaskStatus.OPEN )
        {
            System.err.println( "Task with Closed or Won't Fix status should not shown up here." );
        }
        else
        {
            OngoingWorkEntryService ongoingWorkEntryService = getChronosApp().getServices().getOngoingWorkEntryService();

            OngoingWorkEntryEntityComposite ongoingWorkEntry = ongoingWorkEntryService.getOngoingWorkEntry( task, getChronosApp().getStaff() );

            if( ongoingWorkEntry != null )
            {
                taskOngoingGroupTreeNode.add( new TaskOngoingTreeNode( task, ongoingWorkEntry ) );
            }
            else
            {
                taskOpenedGroupTreeNode.add( new TaskOpenedTreeNode( task ) );
            }
        }
    }

    public class TaskOpenedGroupTreeNode extends AbstractTreeNode
    {
        public String toString()
        {
            return "Opened Task(s)";
        }
    }

    public class TaskOngoingGroupTreeNode extends AbstractTreeNode
    {
        public String toString()
        {
            return "Ongoing Task(s)";
        }
    }

    public String toString()
    {
        return "Task";
    }
}
