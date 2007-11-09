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

import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;
import org.qi4j.chronos.ui.common.AbstractTree;

public class TaskTree extends AbstractTree
{
    public TaskTree()
    {
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

    protected void onDblClick( TreePath path, Object pathComponent, MouseEvent mouseEvent )
    {
        Object userObject = path.getLastPathComponent();

        if( userObject instanceof TaskTreeNode )
        {
            mouseEvent.consume();

            startOrStopTask( (TaskTreeNode) userObject );
        }
    }

    private void startOrStopTask( TaskTreeNode taskTreeNode )
    {
        //TODO bp.
    }

    protected void onClick( TreePath path, Object pathComponent, MouseEvent mouseEvent )
    {
        super.onClick( path, pathComponent, mouseEvent );

        if( mouseEvent.isShiftDown() || mouseEvent.isControlDown() || mouseEvent.isAltDown() )
        {
            return;
        }

        if( pathComponent instanceof TaskTreeNode )
        {
            //TODO bp
        }
    }
}
