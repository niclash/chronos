/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.service.lab;

import org.qi4j.service.Activatable;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;

public class ProjectServiceMixin extends NTTServiceAbstract<Project, ProjectEntityComposite> implements NTTService<Project, ProjectEntityComposite>, Activatable
{
    public ProjectServiceMixin()
    {
        this.clazz = ProjectEntityComposite.class;
    }

    public void activate() throws Exception
    {
    }

    public void passivate() throws Exception
    {
    }
}