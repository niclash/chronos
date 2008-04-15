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
package org.qi4j.chronos.ui.projectrole;

import java.util.List;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectRoleService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.Account;

public abstract class ProjectRoleDataProvider extends AbstractSortableDataProvider<ProjectRole, String>
{
    public String getId( ProjectRole projectRoleEntityComposite )
    {
        return projectRoleEntityComposite.name().get();
    }

    public ProjectRole load( String id )
    {
        return getRoleService().get( getAccount(), id );
    }

    public ProjectRoleService getRoleService()
    {
        return ChronosWebApp.getServices().getProjectRoleService();
    }

    public List<ProjectRole> dataList( int first, int count )
    {
        return getRoleService().findAll( getAccount(), new FindFilter( first, count ) );
    }

    public int getSize()
    {
        return getRoleService().countAll( getAccount() );
    }

    public abstract Account getAccount();
}
