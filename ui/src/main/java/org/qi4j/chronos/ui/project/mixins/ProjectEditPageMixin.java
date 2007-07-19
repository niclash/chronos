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
package org.qi4j.chronos.ui.project.mixins;

import org.qi4j.api.CompositeFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.project.ProjectEditPage;
import org.qi4j.chronos.ui.project.composites.ProjectEditFormComposite;
import org.qi4j.ui.component.UIField;

public final class ProjectEditPageMixin implements ProjectEditPage
{
    @Dependency private CompositeFactory factory;

    private ProjectEntityComposite project;

    @UIField( type = ProjectEditFormComposite.class )
    public Project getProject()
    {
        if( project == null )
        {
            project = factory.newInstance( ProjectEntityComposite.class );

            project.setName( "Chronos" );

            System.err.println( "project.getName() " + project.getName() );
        }

        return project;
    }
}
