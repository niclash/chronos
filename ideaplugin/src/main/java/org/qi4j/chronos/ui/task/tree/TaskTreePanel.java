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

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPopupMenu;
import com.intellij.openapi.project.Project;
import com.intellij.ui.PopupHandler;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.tree.TreePath;
import org.qi4j.chronos.action.ChronosActionConstant;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.ui.common.AbstractPanel;
import org.qi4j.chronos.ui.util.UiUtil;
import org.qi4j.chronos.util.ChronosUtil;

public class TaskTreePanel extends AbstractPanel
{
    private TaskTree taskTree;

    private JScrollPane treeScrollPanel;

    private Project project;
    private ActionManager actionManager;

    public TaskTreePanel( Project project, ActionManager actionManager )
    {
        this.project = project;
        this.actionManager = actionManager;

        renderComponent();
    }

    protected void initComponents()
    {
        taskTree = new TaskTree();

        taskTree.setCellRenderer( new TaskTreeCellRenderer() );
        taskTree.setModel( new TaskTreeModel( project, getTaskService() ) );
        taskTree.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        treeScrollPanel = UiUtil.createScrollPanel( taskTree );

        initListeners();
    }

    private void initListeners()
    {
        PopupHandler popupHandler = new PopupHandler()
        {
            public void invokePopup( Component comp, int x, int y )
            {
                TreePath treePath = taskTree.getPathForLocation( x, y );

                Object obj = treePath.getLastPathComponent();

                ActionPopupMenu popupMenu = null;

                if( obj instanceof TaskOpenedTreeNode )
                {
                    ActionGroup group = (ActionGroup) actionManager.getAction( ChronosActionConstant.TASK_OPENED_GROUP );
                    popupMenu = actionManager.createActionPopupMenu( "POPUP", group );
                }
                else if( obj instanceof TaskOngoingTreeNode )
                {
                    ActionGroup group = (ActionGroup) actionManager.getAction( ChronosActionConstant.TASK_ONGOING_GROUP );
                    popupMenu = actionManager.createActionPopupMenu( "POPUP", group );
                }

                if( popupMenu != null )
                {
                    popupMenu.getComponent().show( comp, x, y );
                }
            }
        };

        taskTree.addMouseListener( popupHandler );
    }

    private TaskService getTaskService()
    {
        return ChronosUtil.getChronosSetting( project ).getServices().getTaskService();
    }

    protected String getLayoutColSpec()
    {
        return "1dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "1dlu:grow";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( treeScrollPanel, cc.xy( 1, 1, "fill, fill" ) );
    }

}
