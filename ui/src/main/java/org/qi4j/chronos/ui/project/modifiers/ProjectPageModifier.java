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
package org.qi4j.chronos.ui.project.modifiers;

import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.ui.InitFailedException;
import org.qi4j.ui.RenderFailedException;
import org.qi4j.ui.component.ComponentLifecycle;
import org.qi4j.ui.component.Container;
import org.qi4j.ui.model.Model;
import org.qi4j.ui.model.ModelComposite;
import org.qi4j.ui.response.Response;

public class ProjectPageModifier
    implements ComponentLifecycle
{
    @Modifies private ComponentLifecycle next;

    @Dependency private CompositeBuilderFactory factory;

    @Uses private Container container;

    public void init() throws InitFailedException
    {
        //Setup model
        Project project = factory.newCompositeBuilder( ProjectEntityComposite.class ).newInstance();
        project.setName( "Chronos" );
//        project.setDescription( "Timesheet for billing application" );

        Model model = factory.newCompositeBuilder( ModelComposite.class ).newInstance();
        model.setObject( project );

        container.setModel( model );

        next.init();
    }

    public void dispose()
    {
        next.dispose();
    }

    public void render( Response response ) throws RenderFailedException
    {
        next.render( response );
    }
}
