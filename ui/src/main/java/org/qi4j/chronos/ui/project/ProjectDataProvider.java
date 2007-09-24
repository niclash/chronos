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
package org.qi4j.chronos.ui.project;

import java.util.List;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class ProjectDataProvider extends AbstractSortableDataProvider<ProjectEntityComposite, String>
{
    public ProjectDataProvider()
    {
    }

    public String getId( ProjectEntityComposite projectEntityComposite )
    {

        return projectEntityComposite.getIdentity();
    }

    public ProjectEntityComposite load( String id )
    {
        return getProjectService().get( id );
    }

    private ProjectService getProjectService()
    {
        return ChronosWebApp.getServices().getProjectService();
    }

    public List<ProjectEntityComposite> dataList( int first, int count )
    {
        return getProjectService().findAll( getAccount(), new FindFilter( first, count ) );
    }

    public int getSize()
    {
        return getProjectService().countAll( getAccount() );
    }

    public abstract AccountEntityComposite getAccount();
}
