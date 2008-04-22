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
package org.qi4j.chronos.service;

import org.qi4j.service.Activatable;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.authentication.AuthenticationService;
import org.qi4j.chronos.service.customer.CustomerService;
import org.qi4j.chronos.service.project.ProjectService;
import org.qi4j.chronos.service.relationship.RelationshipService;
import org.qi4j.chronos.service.systemrole.SystemRoleService;
import org.qi4j.chronos.service.user.UserService;
import org.qi4j.chronos.service.task.TaskService;
import org.qi4j.composite.scope.Service;

import static org.qi4j.composite.NullArgumentException.validateNotNull;

public class AggregatedServiceMixin implements AggregatedService, Activatable
{
    private @Service AccountService accountService;

    private @Service AuthenticationService authenticationService;

    private @Service CustomerService customerService;

    private @Service ProjectService projectService;

    private @Service RelationshipService relationshipService;

    private @Service SystemRoleService systemRoleService;

    private @Service UserService userService;

    private @Service TaskService taskService;

    public AccountService getAccountService()
    {
        return accountService;
    }

    public AuthenticationService getAuthenticationService()
    {
        return authenticationService;
    }

    public CustomerService getCustomerService()
    {
        return customerService;
    }

    public ProjectService getProjectService()
    {
        return projectService;
    }

    public RelationshipService getRelationshipService()
    {
        return relationshipService;
    }

    public SystemRoleService getSystemRoleService()
    {
        return systemRoleService;
    }

    public UserService getUserService()
    {
        return userService;
    }

    public TaskService getTaskService()
    {
        return taskService;
    }

    public void activate() throws Exception
    {
        validateNotNull( "accountService", accountService );
        validateNotNull( "customerService", customerService );
        validateNotNull( "projectService", projectService );
        validateNotNull( "systemRoleService", systemRoleService );
        validateNotNull( "relationshipService", relationshipService );
        validateNotNull( "userService", userService );
        validateNotNull( "authenticationService", authenticationService );
    }

    public void passivate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
