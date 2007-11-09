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

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JScrollPane;
import org.qi4j.chronos.ui.common.AbstractPanel;
import org.qi4j.chronos.ui.util.SwingMiscUtil;

public class TaskTreePanel extends AbstractPanel
{
    private TaskTree taskTree;

    private JScrollPane treeScrollPanel;

    public TaskTreePanel()
    {
        renderComponent();
    }

    protected void initComponents()
    {
        taskTree = new TaskTree();

        taskTree.setCellRenderer( new TaskTreeCellRenderer() );
        taskTree.setModel( new TaskTreeModel() );

        treeScrollPanel = SwingMiscUtil.createScrollPanel( taskTree );
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
