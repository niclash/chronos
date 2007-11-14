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

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;
import org.qi4j.chronos.action.AbstractAction;
import org.qi4j.chronos.action.ChronosActionConstant;
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

    protected void onDoubleClick( TreePath path, Object pathComponent, MouseEvent mouseEvent )
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
        AbstractAction action = null;

        ActionManager actionManager = ActionManager.getInstance();

        if( taskTreeNode instanceof TaskOpenedTreeNode )
        {
            action = (AbstractAction) actionManager.getAction( ChronosActionConstant.WORK_START_ACTION );

        }
        else if( taskTreeNode instanceof TaskOngoingTreeNode )
        {
            action = (AbstractAction) actionManager.getAction( ChronosActionConstant.WORK_STOP_ACTION );
        }

        if( action != null )
        {
            action.actionPerformed( new AnActionEvent( null, DataManager.getInstance().getDataContext( this ),
                                                       ActionPlaces.UNKNOWN, new Presentation(),
                                                       ActionManager.getInstance(), 0 ) );
        }
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
            System.err.println( "Handle OnClick" );
            //TODO bp
        }
    }
}
