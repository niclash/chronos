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
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import javax.swing.JPanel;
import org.qi4j.chronos.ui.common.AbstractToolWindow;

public class TaskToolWindow extends AbstractToolWindow
{
    private static final String TOOL_WINDOW_ID = "Chronos Task";
    private static final String COMPONENT_NAME = "ChronosTaskToolWindow";

    private TaskToolCenterPanel taskToolCenterPanel;

    protected TaskToolWindow( ToolWindowManager toolWindowManager, ActionManager actionManager, Project project )
    {
        super( toolWindowManager, actionManager, project );
    }

    protected String getToolWindowId()
    {
        return TOOL_WINDOW_ID;
    }

    public void initComponent()
    {

    }

    protected JPanel createToolWindowComponent()
    {
        if( taskToolCenterPanel == null )
        {
            taskToolCenterPanel = new TaskToolCenterPanel( getActionManager(), getProject() );
        }

        return taskToolCenterPanel;
    }

    protected ToolWindowAnchor getAnchor()
    {
        return ToolWindowAnchor.RIGHT;
    }

    public String getComponentName()
    {
        return COMPONENT_NAME;
    }

    public TaskToolCenterPanel getTaskToolCenterPanel()
    {
        return taskToolCenterPanel;
    }

    public void disposeComponent()
    {

    }
}

