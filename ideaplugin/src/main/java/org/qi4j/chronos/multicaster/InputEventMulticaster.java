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

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.qi4j.chronos.listener.InputEventListener;
import org.qi4j.chronos.util.listener.EventCallback;

public class InputEventMulticaster extends AbstractEventMulticaster<InputEventListener> implements AWTEventListener
{
    private final long EVENT_MASK = AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK;

    public InputEventMulticaster()
    {
    }

    public void start()
    {
        Toolkit.getDefaultToolkit().addAWTEventListener( this, EVENT_MASK );
    }

    public void stop()
    {
        Toolkit.getDefaultToolkit().removeAWTEventListener( this );
    }

    public void eventDispatched( AWTEvent event )
    {
        InputEvent inputEvent = null;

        if( event instanceof MouseEvent )
        {
            MouseEvent mouseEvent = (MouseEvent) event;

            if( ( mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK ) != 0
                && mouseEvent.getClickCount() == 1 )
            {
                inputEvent = mouseEvent;
            }
        }
        else if( event instanceof KeyEvent )
        {
            KeyEvent keyEvent = (KeyEvent) event;

            if( keyEvent.getID() == KeyEvent.KEY_PRESSED )
            {
                inputEvent = keyEvent;
            }
        }

        if( inputEvent != null )
        {
            final InputEvent tempInputEvent = inputEvent;

            listenerHandler.fireEvent( new EventCallback<InputEventListener>()
            {
                public void callback( InputEventListener listener )
                {
                    listener.newInputEvent( tempInputEvent );
                }
            } );
        }
    }
}

