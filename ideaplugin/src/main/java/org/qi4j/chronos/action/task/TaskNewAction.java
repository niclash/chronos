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
package org.qi4j.chronos.action.task;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import javax.swing.Icon;
import org.qi4j.chronos.action.AbstractAction;
import org.qi4j.chronos.task.TaskAddDialog;

public class TaskNewAction extends AbstractAction
{
    private final static Icon ICON = IconLoader.getIcon( "/general/add.png" );

    public void actionPerformed( AnActionEvent e )
    {
        Project project = (Project) e.getDataContext().getData( DataConstants.PROJECT );

        //show taskAddDialog
        new TaskAddDialog( project ).show();
    }

    public void update( AnActionEvent e )
    {
        super.update( e );

        //TODO fix icon
        e.getPresentation().setIcon( ICON );
    }
}
