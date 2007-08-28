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
package org.qi4j.chronos.service.mocks;

import org.qi4j.api.Composite;
import org.qi4j.api.CompositeBuilder;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.RoleEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.RoleService;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.composites.AccountServiceComposite;
import org.qi4j.chronos.service.composites.CustomerServiceComposite;
import org.qi4j.chronos.service.composites.ProjectServiceComposite;
import org.qi4j.chronos.service.composites.RoleServiceComposite;
import org.qi4j.chronos.service.composites.StaffServiceComposite;

public class MockServicesMixin implements Services
{
    private CompositeBuilderFactory factory;

    private AccountService accountService;
    private CustomerService customerService;
    private ProjectService projectService;
    private RoleService roleService;
    private StaffService staffService;

    public MockServicesMixin( CompositeBuilderFactory factory )
    {
        this.factory = factory;

        accountService = newService( AccountServiceComposite.class );
        customerService = newService( CustomerServiceComposite.class );
        projectService = newService( ProjectServiceComposite.class );
        roleService = newService( RoleServiceComposite.class );
        staffService = newService( StaffServiceComposite.class );

        initDummyData();
    }

    private void initDummyData()
    {
        for( int i = 0; i < 50; i++ )
        {
            AccountEntityComposite account = accountService.newInstance( AccountEntityComposite.class );

            account.setName( "accountName " + i );

            accountService.save( account );
        }

        RoleEntityComposite programmer = roleService.newInstance( RoleEntityComposite.class );
        programmer.setRole( "Programmer" );

        RoleEntityComposite consultant = roleService.newInstance( RoleEntityComposite.class );
        consultant.setRole( "Consultant" );

        RoleEntityComposite projectManager = roleService.newInstance( RoleEntityComposite.class );
        projectManager.setRole( "Project Manager" );

        roleService.save( programmer );
        roleService.save( consultant );
        roleService.save( projectManager );
    }

    public AccountService getAccountService()
    {
        return accountService;
    }

    public CustomerService getCustomerService()
    {
        return customerService;
    }

    public ProjectService getProjectService()
    {
        return projectService;
    }

    public RoleService getRoleService()
    {
        return roleService;
    }

    public StaffService getStaffService()
    {
        return staffService;
    }

    @SuppressWarnings( { "unchecked" } )
    private <T extends Composite> T newService( Class<T> clazz )
    {
        CompositeBuilder<? extends Composite> compositeBuilder = factory.newCompositeBuilder( clazz );

        compositeBuilder.setMixin( EntityService.class, new MockEntityServiceMixin( factory ) );

        return (T) compositeBuilder.newInstance();
    }
}
