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

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.Color;
import org.qi4j.chronos.ui.common.AbstractPanel;
import org.qi4j.chronos.ui.task.tree.TaskTree;
import org.qi4j.chronos.ui.task.tree.TaskTreeNode;
import org.qi4j.chronos.ui.task.tree.TaskTreePanel;

public class TaskToolCenterPanel extends AbstractPanel
{
    private TaskToolBar taskToolBar;
    private TaskTreePanel taskTreePanel;

    private Project project;
    private ActionManager actionManager;

    public TaskToolCenterPanel( ActionManager actionManager, Project project )
    {
        this.project = project;
        this.actionManager = actionManager;

        init();
    }

    public void initComponents()
    {
        taskToolBar = new TaskToolBar( actionManager );
        taskTreePanel = new TaskTreePanel( project, actionManager );

        this.setOpaque( true );
        this.setBackground( Color.white );
    }

    public TaskTree getTaskTree()
    {
        return taskTreePanel.getTaskTree();
    }

    public TaskTreeNode getSelectedTaskTreeNode()
    {
        return taskTreePanel.getSelectedTaskTreeNode();
    }

    public void updateTaskTree()
    {
        taskTreePanel.updateTaskTree();
    }

    public String getLayoutColSpec()
    {
        return "1dlu:grow";
    }

    public String getLayoutRowSpec()
    {
        return "18dlu, 1dlu:grow";
    }

    public void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( taskToolBar, cc.xy( 1, 1, "fill, fill" ) );
        builder.add( taskTreePanel, cc.xy( 1, 2, "fill, fill" ) );
    }
}
