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
package org.qi4j.chronos.service.task;

import org.qi4j.service.Activatable;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Service;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.Identity;

import static org.qi4j.composite.NullArgumentException.validateNotNull;

public class TaskServiceMixin implements TaskService, Activatable
{
    private @Structure UnitOfWorkFactory factory;

    private @Service AccountService accountService;

    public Task get( String taskId )
    {
        validateNotNull( "taskId", taskId );

/*
        if( factory.currentUnitOfWork() == null || !factory.currentUnitOfWork().isOpen() )
        {
            System.err.println( "Unable to get unit of work" );

            return null;
        }

        UnitOfWork unitOfWork = factory.currentUnitOfWork();

        return unitOfWork.find( taskId, TaskEntityComposite.class );
*/

        for( Account account : accountService.findAvailableAccounts() )
        {
            for( Project project : account.projects() )
            {
                for( Task task : project.tasks() )
                {
                    if( taskId.equals( ( (Identity) task ).identity().get() ) )
                    {
                        return task;
                    }
                }
            }
        }

        return null;
    }

    public void activate() throws Exception
    {
        validateNotNull( "factory", factory );
        validateNotNull( "accountService", accountService );
    }

    public void passivate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
