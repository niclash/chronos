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

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class InputEventBroadcasterImpl implements InputEventBroadcaster, AWTEventListener
{
    private List<InputEventListener> listeners = new ArrayList<InputEventListener>();

    private final long EVENT_MASK = AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK;

    public void start()
    {
        Toolkit.getDefaultToolkit().addAWTEventListener( this, EVENT_MASK );
    }

    public void stop()
    {
        Toolkit.getDefaultToolkit().removeAWTEventListener( this );
    }

    public synchronized void addInputEventListener( InputEventListener inputEventListener )
    {
        if( !listeners.contains( inputEventListener ) )
        {
            listeners.add( inputEventListener );
        }
    }

    public synchronized void removeInputEventListener( InputEventListener inputEventListener )
    {
        listeners.remove( inputEventListener );
    }

    private synchronized InputEventListener[] getListeners()
    {
        return listeners.toArray( new InputEventListener[listeners.size()] );
    }

    public void eventDispatched( AWTEvent event )
    {
        if( event instanceof MouseEvent )
        {
            MouseEvent mouseEvent = (MouseEvent) event;

            if( ( mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK ) != 0
                && mouseEvent.getClickCount() == 1 )
            {
                fireEvent( mouseEvent );
            }
        }
        else if( event instanceof KeyEvent )
        {
            KeyEvent keyEvent = (KeyEvent) event;

            if( keyEvent.getID() == KeyEvent.KEY_PRESSED )
            {
                fireEvent( keyEvent );
            }
        }
    }

    private void fireEvent( InputEvent event )
    {
        InputEventListener[] listeners = getListeners();

        for( InputEventListener listener : listeners )
        {
            listener.newInputEvent( event );
        }
    }
}
