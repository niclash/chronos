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
package org.qi4j.chronos.ui.projectowner;

import java.util.List;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.service.ProjectOwnerService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class ProjectOwnerDataProvider extends AbstractSortableDataProvider<ProjectOwnerEntityComposite>
{
    public String getId( ProjectOwnerEntityComposite projectOwnerEntityComposite )
    {
        return projectOwnerEntityComposite.getIdentity();
    }

    public ProjectOwnerEntityComposite load( String id )
    {
        return getProjectOwnerService().get( id );
    }

    private ProjectOwnerService getProjectOwnerService()
    {
        return ChronosWebApp.getServices().getProjectOwnerService();
    }

    public List<ProjectOwnerEntityComposite> dataList( int first, int count )
    {
        return getProjectOwnerService().findAll( getAccount() );
    }

    private AccountEntityComposite getAccount()
    {
        return ChronosWebApp.getServices().getAccountService().get( getAccountId() );
    }

    public int size()
    {
        return getProjectOwnerService().countAll( getAccount() );
    }

    public abstract String getAccountId();
}