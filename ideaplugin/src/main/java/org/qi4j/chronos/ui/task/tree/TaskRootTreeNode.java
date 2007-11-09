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
package org.qi4j.chronos.ui.task.tree;

import org.qi4j.chronos.model.TaskStatus;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.ui.common.AbstractTreeNode;

public class TaskRootTreeNode extends AbstractTreeNode
{
    private ClosedTaskGroupTreeNode taskClosedGroupTreeNode;
    private WontFixTaskGroupTreeNode taskWontFixGroupTreeNode;

    public TaskRootTreeNode()
    {
        initGroupTreeNodes();
        loadTaskTreeNode();
    }

    private void loadTaskTreeNode()
    {
        //TODO
    }

    private void initGroupTreeNodes()
    {
        taskClosedGroupTreeNode = new ClosedTaskGroupTreeNode();
        taskWontFixGroupTreeNode = new WontFixTaskGroupTreeNode();

        this.add( taskClosedGroupTreeNode );
        this.add( taskWontFixGroupTreeNode );
    }

    public void addTaskTreeNode( TaskEntityComposite task )
    {
        if( task.getTaskStatus() == TaskStatus.CLOSED )
        {
            taskClosedGroupTreeNode.add( new TaskClosedTreeNode( task ) );
        }
        else if( task.getTaskStatus() == TaskStatus.WONT_FIX )
        {
            taskWontFixGroupTreeNode.add( new TaskWontFixTreeNode( task ) );
        }
        else
        {
            System.err.println( "Task with non Closed or Won't Fix status should not shown up here." );
        }
    }

    public class WontFixTaskGroupTreeNode extends AbstractTreeNode
    {
        public String toString()
        {
            return "Won't Fix";
        }
    }

    public class ClosedTaskGroupTreeNode extends AbstractTreeNode
    {
        public String toString()
        {
            return "Closed";
        }
    }

    public String toString()
    {
        return "Task";
    }
}
