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
package org.qi4j.chronos.service.project;

import org.qi4j.service.Activatable;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.composite.scope.This;
import org.qi4j.composite.scope.Service;

import static org.qi4j.composite.NullArgumentException.validateNotNull;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import java.util.HashMap;
import java.util.Map;

public class ProjectServiceMixin implements ProjectService, Activatable
{
    private @This ProjectServiceConfiguration config;

    private @Service AccountService accountService;

    public void add( Account account, Project project )
    {
        validateNotNull( "account", account );
        validateNotNull( "project", project );

        Map<String, String> map = config.map().get();

        if( !map.containsKey( ( (Identity) project).identity().get() ) )
        {
            map.put( ( (Identity) project).identity().get(), ( (Identity) account).identity().get() );
        }
    }

    public Account getAccount( UnitOfWork unitOfWork, Project project )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "project", project );

        for( Account account : accountService.findAll() )
        {
            if( contains( account, project ) )
            {
                return account;
            }
        }
        /*Map<String, String> map = config.map().get();

        if( map.containsKey( ( (Identity) project ).identity().get() ) )
        {
            return unitOfWork.find( map.get( ( (Identity) project).identity().get() ), AccountEntityComposite.class );
        }*/
        return null;
    }

    private boolean contains( HasProjects hasProjects, Project project )
    {
        return hasProjects.projects().contains( project );
    }

    public void activate() throws Exception
    {
        validateNotNull( "config", config );
        validateNotNull( "accountService", accountService );

        if( config.map().get() == null )
        {
            config.map().set( new HashMap<String, String>() );
        }
    }

    public void passivate() throws Exception
    {
    }
}
