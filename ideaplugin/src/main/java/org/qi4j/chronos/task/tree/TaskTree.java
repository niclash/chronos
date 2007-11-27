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

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import java.awt.Container;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import org.qi4j.chronos.action.ChronosActionConstant;
import org.qi4j.chronos.action.task.TaskBaseAction;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.common.AbstractTree;
import org.qi4j.chronos.task.TaskListComponent;
import org.qi4j.chronos.util.UiUtil;
import org.qi4j.chronos.util.ChronosUtil;

public class TaskTree extends AbstractTree implements TaskListComponent
{
    private Project project;

    public TaskTree( Project project )
    {
        this.project = project;
    }

    public String getToolTipText( MouseEvent mouseEvent )
    {
        Object tip = null;
        TreePath path = getPathForLocation( mouseEvent.getX(), mouseEvent.getY() );

        if( path != null )
        {
            tip = path.getLastPathComponent();
        }
        return tip == null ? null : tip.toString();
    }

    protected void onDoubleClick( TreePath path, Object pathComponent, MouseEvent mouseEvent )
    {
        Object userObject = path.getLastPathComponent();

        if( userObject instanceof TaskTreeNode )
        {
            mouseEvent.consume();

            startOrStopTask( (TaskTreeNode) userObject, mouseEvent );
        }
    }

    private void startOrStopTask( TaskTreeNode taskTreeNode, MouseEvent mouseEvent )
    {
        TaskBaseAction action = null;

        ActionManager actionManager = ActionManager.getInstance();

        if( taskTreeNode instanceof TaskOpenedTreeNode )
        {
            action = (TaskBaseAction) actionManager.getAction( ChronosActionConstant.ONGOING_WORKENTRY_NEW_ACTION );

        }
        else if( taskTreeNode instanceof TaskOngoingTreeNode )
        {
            action = (TaskBaseAction) actionManager.getAction( ChronosActionConstant.ONGOING_WORKENTRY_STOP_ACTION );
        }

        if( action != null )
        {
            action.actionPerformed( new AnActionEvent(
                mouseEvent, DataManager.getInstance().getDataContext( this ),
                ActionPlaces.UNKNOWN, new Presentation(),
                ActionManager.getInstance(), 0 ) );
        }
    }


    public TaskEntityComposite getSelectedTask()
    {
        TreePath treePath = this.getSelectionPath();

        Object obj = treePath.getLastPathComponent();

        if( obj instanceof TaskTreeNode )
        {
            return ( (TaskTreeNode) obj ).getTask();
        }

        return null;
    }

    public TaskEntityComposite[] getSelectedTasks()
    {
        //bp. no support for multiple selection at this moment
        return new TaskEntityComposite[]{ getSelectedTask() };
    }

    public void refreshList()
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            public void run()
            {
                TaskTreeModel taskTreeModel = (TaskTreeModel) TaskTree.this.getModel();

                taskTreeModel.updateModel( project, getTaskService() );

                //expand all treenodes
                UiUtil.expandAll( TaskTree.this );
            }
        } );
    }

    public Container getComponent()
    {
        return this;
    }

    private TaskService getTaskService()
    {
        return ChronosUtil.getChronosSetting( project ).getServices().getTaskService();
    }
}