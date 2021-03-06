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
package org.qi4j.chronos.common;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.peer.PeerFactory;
import com.intellij.ui.content.Content;
import java.awt.EventQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class AbstractToolWindow
{
    private final ToolWindowManager toolWindowManager;
    private final ActionManager actionManager;
    private final Project project;

    protected AbstractToolWindow( ToolWindowManager toolWindowManager, ActionManager actionManager, Project project )
    {
        this.project = project;
        this.toolWindowManager = toolWindowManager;
        this.actionManager = actionManager;
    }

    protected ActionManager getActionManager()
    {
        return actionManager;
    }

    protected Project getProject()
    {
        return project;
    }

    protected ToolWindowManager getToolWindowManager()
    {
        return toolWindowManager;
    }

    public void register()
    {
        JPanel panel = createToolWindowComponent();

        PeerFactory peerFactory = PeerFactory.getInstance();

        Content content = peerFactory.getContentFactory().createContent( panel, "", false );

// TODO: FIX FOR IDEA6
//        ToolWindow toolWindow = toolWindowManager.registerToolWindow( getToolWindowId(), true, getAnchor() );
//        toolWindow.getContentManager().addContent( content );
//
//        //TODO fix icon
//        toolWindow.setIcon( IconLoader.getIcon( "/general/toolWindowPalette.png" ) );
    }

    public void unregister()
    {
        if( toolWindowManager != null )
        {
            if( toolWindowManager.getToolWindow( getToolWindowId() ) != null )
            {
                toolWindowManager.unregisterToolWindow( getToolWindowId() );
            }
        }

        disposeComponent();
    }

    public void expandToolWindow()
    {
        final Semaphore semaphore = new Semaphore( 1 );
        semaphore.tryAcquire();
        SwingUtilities.invokeLater( new Runnable()
        {
            public void run()
            {
                toolWindowManager.getToolWindow( getToolWindowId() ).show( new Runnable()
                {
                    public void run()
                    {
                        semaphore.release();
                    }
                } );
            }
        } );
        waitForOpen( semaphore );
    }

    private void waitForOpen( Semaphore semaphore )
    {
        if( !EventQueue.isDispatchThread() )
        {
            try
            {
                semaphore.tryAcquire( 1, TimeUnit.SECONDS );
            }
            catch( InterruptedException e )
            {
                //unlikely happen
                e.printStackTrace();
            }
        }
    }

    public void disposeComponent()
    {
    }

    protected abstract String getToolWindowId();

    protected abstract JPanel createToolWindowComponent();

    protected abstract ToolWindowAnchor getAnchor();
}

