/*
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
package org.qi4j.ui.component.mixins;

import org.qi4j.ui.component.ComponentLifecycle;
import org.qi4j.ui.RenderFailedException;
import org.qi4j.ui.InitFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ComponentLifecycleMixin implements ComponentLifecycle
{
    public void init() throws InitFailedException
    {
        // real action is done in modifiers
    }

    public void dispose()
    {
        // real action is done in modifiers
    }

    public void render( HttpServletRequest request, HttpServletResponse response ) throws RenderFailedException
    {
        // real action is done in modifiers
    }
}
