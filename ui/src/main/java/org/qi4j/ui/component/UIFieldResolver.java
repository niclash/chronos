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
package org.qi4j.ui.component;

import org.qi4j.api.CompositeFactory;
import org.qi4j.ui.model.Model;
import org.qi4j.ui.model.ModelComposite;

public final class UIFieldResolver
{
    public static Component resolveUIField( CompositeFactory factory, UIField uiField, Object obj )
    {
        Class<? extends Component> componentClass = uiField.type();
        Component component = factory.newInstance( componentClass );

        Model model = factory.newInstance( ModelComposite.class );
        model.setModel( obj );
        component.setModel( model );

        return component;
    }
}