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

import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.authentication.AuthenticationService;
import org.qi4j.chronos.service.customer.CustomerService;
import org.qi4j.chronos.service.project.ProjectService;
import org.qi4j.chronos.service.relationship.RelationshipService;
import org.qi4j.chronos.service.systemrole.SystemRoleService;
import org.qi4j.chronos.service.user.UserService;
import org.qi4j.chronos.service.task.TaskService;

public interface AggregatedService
{
    AccountService getAccountService();

    AuthenticationService getAuthenticationService();

    CustomerService getCustomerService();

    ProjectService getProjectService();

    RelationshipService getRelationshipService();

    SystemRoleService getSystemRoleService();

    UserService getUserService();

    TaskService getTaskService();
}
