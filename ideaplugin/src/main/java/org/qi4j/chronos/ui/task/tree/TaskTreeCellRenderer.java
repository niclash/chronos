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

import com.intellij.openapi.util.IconLoader;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.qi4j.chronos.ui.common.AbstractTreeNode;

public class TaskTreeCellRenderer extends DefaultTreeCellRenderer
{
    //TODO bp. fix these icons
    private final static Icon TASK_ROOT_ICON = IconLoader.getIcon( "/general/add.png" );
    private final static Icon TASK_WONT_FIX_GROUP_ICON = IconLoader.getIcon( "/general/ideOptions.png" );
    private final static Icon TASK_CLOSED_GROUP_ICON = IconLoader.getIcon( "/general/reset.png" );
    private final static Icon TASK_WONT_FIX_ICON = IconLoader.getIcon( "/general/template.png" );
    private final static Icon TASK_CLOSED_ICON = IconLoader.getIcon( "/general/addJdk.png" );

    public Component getTreeCellRendererComponent(
        JTree tree, Object value, boolean sel, boolean expanded,
        boolean leaf, int row, boolean hasFocus )
    {
        super.getTreeCellRendererComponent( tree, value, sel,
                                            expanded, leaf, row, hasFocus );

        AbstractTreeNode tempNode = (AbstractTreeNode) value;

        if( tempNode instanceof TaskRootTreeNode )
        {
            setIcon( TASK_ROOT_ICON );
        }
        else if( tempNode instanceof TaskRootTreeNode.ClosedTaskGroupTreeNode )
        {
            setIcon( TASK_CLOSED_GROUP_ICON );
        }
        else if( tempNode instanceof TaskRootTreeNode.WontFixTaskGroupTreeNode )
        {
            setIcon( TASK_WONT_FIX_GROUP_ICON );
        }
        else if( tempNode instanceof TaskClosedTreeNode )
        {
            setIcon( TASK_CLOSED_ICON );
        }
        else if( tempNode instanceof TaskWontFixTreeNode )
        {
            setIcon( TASK_WONT_FIX_ICON );
        }

        return this;
    }
}
