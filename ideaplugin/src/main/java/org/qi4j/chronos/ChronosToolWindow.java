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

package org.qi4j.chronos;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import javax.swing.JPanel;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.qi4j.chronos.activity.ActivityManager;
import org.qi4j.chronos.common.AbstractToolWindow;
import org.qi4j.chronos.multicaster.IdleEventMulticaster;
import org.qi4j.chronos.multicaster.InputEventMulticaster;
import org.qi4j.chronos.workentry.WorkEntryProducer;

public class ChronosToolWindow extends AbstractToolWindow
{
    private static final String TOOL_WINDOW_ID = "Chronos";
    private static final String COMPONENT_NAME = "ChronosToolWindow";

    private ChronosToolMainPanel chronosToolMainPanel;

    private WorkEntryProducer workEntryProducer;

    private InputEventMulticaster inputEventBroadcaster;
    private IdleEventMulticaster idleEventBroadcaster;
    private ActivityManager manager;

    protected ChronosToolWindow( ToolWindowManager toolWindowManager, ActionManager actionManager, Project project )
    {
        super( toolWindowManager, actionManager, project );
    }

    public static ChronosToolWindow getInstance( Project project )
    {
        return project.getComponent( ChronosToolWindow.class );
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

            workEntryProducer.addWorkEntryProducerListener( chronosToolMainPanel );
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

        inputEventBroadcaster = new InputEventMulticaster();
        idleEventBroadcaster = new IdleEventMulticaster( inputEventBroadcaster );

        manager = new ActivityManager( getProject() );

        workEntryProducer = new WorkEntryProducer( getProject(), manager,
                                                   idleEventBroadcaster, inputEventBroadcaster );
        inputEventBroadcaster.start();
        idleEventBroadcaster.start();
        manager.start();
    }

    public void disposeComponent()
    {
        inputEventBroadcaster.stop();
        idleEventBroadcaster.stop();
        manager.stop();
    }
}
