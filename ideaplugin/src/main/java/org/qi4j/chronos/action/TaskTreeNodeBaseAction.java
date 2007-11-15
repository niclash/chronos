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
package org.qi4j.chronos.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.qi4j.chronos.action.AbstractAction;
import org.qi4j.chronos.ui.task.TaskToolWindow;
import org.qi4j.chronos.ui.task.tree.TaskTreeNode;
import org.qi4j.chronos.util.ChronosUtil;

public abstract class TaskTreeNodeBaseAction<T extends TaskTreeNode> extends AbstractAction
{
    public final void actionPerformed( AnActionEvent e )
    {
        TaskToolWindow taskToolWindow = ChronosUtil.getTaskToolWindow( e );

        T taskTreeNode = (T) taskToolWindow.getTaskToolCenterPanel().getSelectedTaskTreeNode();

        execute( taskTreeNode, e );
    }

    public abstract void execute( T taskTreeNode, AnActionEvent e );
}
