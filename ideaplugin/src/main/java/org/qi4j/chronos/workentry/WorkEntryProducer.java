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
package org.qi4j.chronos.workentry;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerAdapter;
import java.awt.event.InputEvent;
import java.util.Date;
import org.qi4j.chronos.ChronosApp;
import org.qi4j.chronos.ChronosProjectComponent;
import org.qi4j.chronos.IdleEvent;
import org.qi4j.chronos.IdleEventListener;
import org.qi4j.chronos.InputEventListener;
import org.qi4j.chronos.activity.Activity;
import org.qi4j.chronos.activity.ActivityManager;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.multicaster.IdleEventMulticaster;
import org.qi4j.chronos.multicaster.InputEventMulticaster;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.chronos.util.listener.EventCallback;
import org.qi4j.chronos.util.listener.ListenerHandler;

public class WorkEntryProducer
{
    /**
     * Default minimum period of time in minute for each workentry.
     */
    public final static int MIN_PERIOD = 0;

    private ActivityManager activityManager;
    private IdleEventMulticaster idleEventMulticaster;
    private InputEventMulticaster inputEventMulticaster;

    private Date lastReadTime;

    private Project project;

    private ListenerHandler<WorkEntryProducerListener> listenerHandler;

    public WorkEntryProducer( Project project, ActivityManager activityManager, IdleEventMulticaster idleEventMulticaster, InputEventMulticaster inputEventMulticaster )
    {
        this.project = project;

        this.activityManager = activityManager;
        this.idleEventMulticaster = idleEventMulticaster;
        this.inputEventMulticaster = inputEventMulticaster;

        listenerHandler = new ListenerHandler<WorkEntryProducerListener>();

        init();
    }

    private void init()
    {
        idleEventMulticaster.addListener( new IdleEventListener()
        {
            public void onIdle( IdleEvent event )
            {
                handleOnIdle();
            }
        } );

        inputEventMulticaster.addListener( new InputEventListener()
        {
            public void newInputEvent( InputEvent event )
            {
                handleNewInputEvent();
            }
        } );

        ProjectManager.getInstance().addProjectManagerListener( project, new ProjectManagerAdapter()
        {
            public void projectOpened( Project project )
            {
                handleProjectOpened();
            }

            public void projectClosed( Project project )
            {
                handleProjectClosed();
            }
        } );
    }

    public void addWorkEntryProducerListener( WorkEntryProducerListener listener )
    {
        listenerHandler.addListener( listener );
    }

    public void removeWorkEntryProducerListener( WorkEntryProducerListener listener )
    {
        listenerHandler.removeListener( listener );
    }

    private void handleProjectOpened()
    {
        checkIfStartNewWorkEntry();
    }

    private void handleProjectClosed()
    {
        checkIfCreateNewWorkEntry();
    }

    private void handleOnIdle()
    {
        checkIfCreateNewWorkEntry();
    }

    private void handleNewInputEvent()
    {
        checkIfStartNewWorkEntry();
    }

    private void checkIfStartNewWorkEntry()
    {
        synchronized( this )
        {
            if( lastReadTime == null )
            {
                lastReadTime = new Date();
            }
        }
    }

    private void checkIfCreateNewWorkEntry()
    {
        synchronized( this )
        {
            if( lastReadTime != null )
            {
                Date now = new Date();

                long diff = now.getTime() - lastReadTime.getTime();

                long minuteDiff = diff / ( 60 * 1000 );

                if( minuteDiff > MIN_PERIOD )
                {
                    addNewWorkEntry( lastReadTime, now );
                }

                lastReadTime = null;

                activityManager.removeAllActivities();
            }
        }
    }

    private void addNewWorkEntry( Date startedDate, Date endDate )
    {
        ChronosApp chronosApp = ChronosProjectComponent.getInstance( project ).getChronosApp();

        Services services = chronosApp.getServices();

        WorkEntryService workEntryService = services.getWorkEntryService();

        final WorkEntry workEntry = workEntryService.newInstance( WorkEntry.class );

        workEntry.createdDate().set( endDate );
        workEntry.startTime().set( startedDate );
        workEntry.endTime().set( endDate );
        workEntry.title().set( "Auto generated" );
        workEntry.description().set( getWorkEntryDescription() );
        workEntry.projectAssignee().set( chronosApp.getProjectAssignee() );

        //get associated task
        Task associatedTask = chronosApp.getAssociatedTask();

        //add new workEntry to associated task
        associatedTask.workEntries().add( workEntry );

        services.getTaskService().update( associatedTask );

        System.err.println( "New WorkEntry Created  " + endDate );

        //fire workEntry added event
        listenerHandler.fireEvent( new EventCallback<WorkEntryProducerListener>()
        {
            public void callback( WorkEntryProducerListener listener )
            {
                listener.workEntryAdded( workEntry );
            }
        } );
    }

    private String getWorkEntryDescription()
    {
        Activity[] activities = activityManager.getActivities();

        StringBuffer buf = new StringBuffer();

        for( Activity activity : activities )
        {
            if( buf.length() != 0 )
            {
                buf.append( "\n" );
            }

            buf.append( activity.getComment() );
        }

        return buf.toString();
    }
}
