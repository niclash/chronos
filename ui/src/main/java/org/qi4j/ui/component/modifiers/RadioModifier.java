/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.ui.component.modifiers;

import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.ui.InitFailedException;
import org.qi4j.ui.RenderFailedException;
import org.qi4j.ui.response.Response;
import org.qi4j.ui.component.ComponentLifecycle;
import org.qi4j.ui.component.Radio;

public final class RadioModifier implements ComponentLifecycle
{
    @Modifies private ComponentLifecycle next;
    @Uses private Radio radio;

    public void init() throws InitFailedException
    {
        next.init();
    }

    public void dispose()
    {
        next.dispose();
    }

    public void render( Response response ) throws RenderFailedException
    {
        //TODO bp. support radio group
        response.write( "<input type=\"radio\" name=\"" + radio.getName() + "\"/>" );
        next.render( response );
    }
}