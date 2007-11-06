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
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.qi4j.chronos.ui.common.AbstractPanel;

public class TaskToolCenterPanel extends AbstractPanel
{
    private final ActionManager actionManager;

    private TaskToolBar taskToolBar;

    public TaskToolCenterPanel( ActionManager actionManager )
    {
        this.actionManager = actionManager;

        renderComponent();
    }

    public void initComponents()
    {
        taskToolBar = new TaskToolBar( actionManager );

        this.setOpaque( true );
        this.setBackground( Color.white );
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
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode( "Hello" );

        //TODO dummy component
        JTree jTree = new JTree( new DefaultTreeModel( treeNode ) );

        JScrollPane scrollPane = new JScrollPane( jTree );

        builder.add( taskToolBar, cc.xy( 1, 1, "fill, fill" ) );
        builder.add( scrollPane, cc.xy( 1, 2, "fill, fill" ) );
    }
}
