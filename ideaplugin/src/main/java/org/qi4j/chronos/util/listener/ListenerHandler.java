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
package org.qi4j.chronos.util.listener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListenerHandler<T extends EventListener>
{
    private final static int THREAD_POOL_SIZE = 10;

    private List<T> listeners;

    private static ExecutorService executorService;

    static
    {
        executorService = Executors.newFixedThreadPool( THREAD_POOL_SIZE );
    }

    public ListenerHandler()
    {
        listeners = new ArrayList<T>();
    }

    public synchronized void addListener( T t )
    {
        if( !listeners.contains( t ) )
        {
            listeners.add( t );
        }
    }

    public synchronized void removeListener( T t )
    {
        listeners.remove( t );
    }

    @SuppressWarnings( "unchecked" )
    public synchronized List<T> getListeners()
    {
        List<T> tempList = new ArrayList<T>();

        tempList.addAll( listeners );

        return tempList;
    }

    public void fireEvent( final EventCallback<T> eventCallback )
    {
        List<T> tempList = getListeners();

        for( final T t : tempList )
        {
            executorService.execute( new Runnable()
            {
                public void run()
                {
                    eventCallback.callback( t );
                }
            } );
        }
    }
}
