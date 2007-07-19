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
package org.qi4j.ui.association.mixins;

import java.util.ArrayList;
import java.util.List;
import org.qi4j.ui.association.HasComponents;
import org.qi4j.ui.component.Component;

public final class HasComponentsMixin implements HasComponents
{
    private final List<Component> components;

    public HasComponentsMixin()
    {
        components = new ArrayList<Component>();
    }

    public void addComponent( Component component )
    {
        components.add( component );
    }

    public void removeComponent( Component component )
    {
        components.remove( component );
    }

    public List<Component> getComponents()
    {
        return components;
    }

}
