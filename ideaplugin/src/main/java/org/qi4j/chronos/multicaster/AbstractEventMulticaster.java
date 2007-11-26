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

import java.util.EventListener;
import java.util.List;
import org.qi4j.chronos.util.listener.ListenerHandler;

public abstract class AbstractEventMulticaster<T extends EventListener>
{
    protected final ListenerHandler<T> listenerHandler;

    public AbstractEventMulticaster()
    {
        listenerHandler = new ListenerHandler<T>();
    }

    public void addListener( T t )
    {
        listenerHandler.addListener( t );
    }

    public void removeListener( T t )
    {
        listenerHandler.removeListener( t );
    }

    public List<T> getListeners()
    {
        return listenerHandler.getListeners();
    }

    public abstract void start();

    public abstract void stop();
}
