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
package org.qi4j.chronos.multicaster;

import java.awt.event.InputEvent;
import java.util.Date;
import org.qi4j.chronos.IdleEvent;
import org.qi4j.chronos.IdleEventListener;
import org.qi4j.chronos.InputEventListener;
import org.qi4j.chronos.util.listener.EventCallback;

public class IdleEventMulticaster extends AbstractEventMulticaster<IdleEventListener>
{
    /**
     * default period in minisecond for repeating check if IDE is idle.
     */
    public final static int CHECK_PERIOD = 1000;

    /**
     * default idle time in minute.
     */
    public final static int IDLE_TIME = 1;

    private Thread workerThread;

    private Date lastReadTime = null;

    public IdleEventMulticaster( InputEventMulticaster inputEventBroadcaster )
    {
        initListeners( inputEventBroadcaster );
    }

    private void initIdleWorkerThread()
    {
        workerThread = new Thread()
        {
            public void run()
            {
                doCheckIfIdle();
            }
        };

        workerThread.setDaemon( true );
        workerThread.start();
    }

    private void doCheckIfIdle()
    {
        try
        {
            while( true )
            {
                Thread.sleep( CHECK_PERIOD );

                if( lastReadTime != null )
                {
                    Date now = new Date();

                    long diff = now.getTime() - lastReadTime.getTime();

                    long minuteDiff = diff / ( 60 * 1000 );

                    if( minuteDiff >= IDLE_TIME )
                    {
                        fireIdleEvent( lastReadTime, now );

                        lastReadTime = now;
                    }
                }
            }
        }
        catch( InterruptedException ignored )
        {
            //ignored
        }
        finally
        {
            lastReadTime = null;
            workerThread = null;
        }
    }

    private void fireIdleEvent( final Date lastReadTime, final Date now )
    {
        listenerHandler.fireEvent( new EventCallback<IdleEventListener>()
        {
            public void callback( IdleEventListener idleEventListener )
            {
                idleEventListener.onIdle( new IdleEvent( lastReadTime, now ) );
            }
        } );
    }

    private void initListeners( InputEventMulticaster inputEventBroadcaster )
    {
        inputEventBroadcaster.addListener( new InputEventListener()
        {
            public void newInputEvent( InputEvent event )
            {
                handleInputEvent( event );
            }
        } );
    }

    public synchronized void start()
    {
        if( workerThread == null )
        {
            initIdleWorkerThread();
        }
    }

    public synchronized void stop()
    {
        if( workerThread != null )
        {
            workerThread.interrupt();
        }
    }

    private void handleInputEvent( InputEvent event )
    {
        //just update the lastReadTime
        lastReadTime = new Date();
    }
}
