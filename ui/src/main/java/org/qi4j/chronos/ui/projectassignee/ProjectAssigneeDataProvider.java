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
package org.qi4j.chronos.ui.projectassignee;

import java.util.List;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectAssigneeService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class ProjectAssigneeDataProvider extends AbstractSortableDataProvider<ProjectAssigneeEntityComposite, String>
{
    public int getSize()
    {
        return getProjectAssigneeService().countAll( getProject() );
    }

    public String getId( ProjectAssigneeEntityComposite t )
    {
        return t.identity().get();
    }

    public ProjectAssigneeEntityComposite load( String s )
    {
        return getProjectAssigneeService().get( s );
    }

    public List<ProjectAssigneeEntityComposite> dataList( int first, int count )
    {
        return getProjectAssigneeService().findAll( getProject(), new FindFilter( first, count ) );
    }

    private ProjectAssigneeService getProjectAssigneeService()
    {
        return ChronosWebApp.getServices().getProjectAssigneeService();
    }

    public abstract ProjectEntityComposite getProject();
}
