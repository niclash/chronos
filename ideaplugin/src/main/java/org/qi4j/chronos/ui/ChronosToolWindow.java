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

package org.qi4j.chronos.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import javax.swing.JPanel;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.qi4j.chronos.ui.common.AbstractToolWindow;

public class ChronosToolWindow extends AbstractToolWindow
{
    private static final String TOOL_WINDOW_ID = "Chronos";
    private static final String COMPONENT_NAME = "ChronosToolWindow";

    private ChronosToolMainPanel chronosToolMainPanel;

    protected ChronosToolWindow( ToolWindowManager toolWindowManager, ActionManager actionManager, Project project )
    {
        super( toolWindowManager, actionManager, project );
    }

    protected String getToolWindowId()
    {
        return TOOL_WINDOW_ID;
    }

    protected JPanel createToolWindowComponent()
    {
        if( chronosToolMainPanel == null )
        {
            chronosToolMainPanel = new ChronosToolMainPanel( getProject(), getActionManager() );
        }

        return chronosToolMainPanel;
    }

    protected ToolWindowAnchor getAnchor()
    {
        return ToolWindowAnchor.RIGHT;
    }

    @NonNls @NotNull public String getComponentName()
    {
        return COMPONENT_NAME;
    }

    public void initComponent()
    {
        //nothing here
    }
}
