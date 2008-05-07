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
package org.qi4j.chronos.service.test;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.service.account.AccountServiceComposite;
import org.qi4j.chronos.service.account.AccountServiceConfiguration;
import org.qi4j.chronos.service.project.ProjectService;
import org.qi4j.chronos.service.project.ProjectServiceComposite;
import org.qi4j.chronos.service.project.ProjectServiceConfiguration;
import org.qi4j.chronos.test.AbstractCommonTest;
import static org.qi4j.composite.NullArgumentException.*;

public class ProjectServiceTest extends AbstractCommonTest
{
    private Account account;

    private List<Customer> customers;

    private List<Project> projects;

    private ProjectService projectService;

    private AccountService accountService;

    @Before @Override public void setUp() throws Exception
    {
        super.setUp();

        projectService = serviceLocator.findService( ProjectServiceComposite.class ).get();
        accountService = serviceLocator.findService( AccountServiceComposite.class ).get();
    }


    @Override public void assemble( ModuleAssembly assembler ) throws AssemblyException
    {
        super.assemble( assembler );

        assembler.addServices( AccountServiceComposite.class, ProjectServiceComposite.class );
        assembler.addComposites( AccountServiceConfiguration.class, ProjectServiceConfiguration.class );
    }

    @Test public void initProjectTest() throws Exception
    {
        initData();
        projects = getProjects( customers );

        for( Project project : projects )
        {
            account.projects().add( project );
        }

        unitOfWork = complete( unitOfWork );

        validateNotNull( "projects", projects );
        assertEquals( "There should be 1 project for each customer!!!!", customers.size(), projects.size() );
    }

    @Test public void getAccountTest() throws Exception
    {
        initData();

        projects = getProjects( customers );
        for( Project project : projects )
        {
            account.projects().add( project );
            projectService.add( account, project );
        }

        accountService.add( account );
        accountService.add( newAccount( "Secondary Account", "secondary" ) );
        accountService.add( newAccount( "Tertiary Account", "tertiary" ) );

        unitOfWork = complete( unitOfWork );

        validateNotNull( "projects", projects );
        assertEquals( "There should be 1 project for each customer!!!!", customers.size(), projects.size() );

        Project project = unitOfWork.dereference( projects.get( 0 ) );

//        Account find = projectService.getAccount( unitOfWork, project );
        Account find = projectService.getAccount( project );

        assertNotNull( "Unable to find Account!!!", find );
        assertEquals( "Account name is different!!!", "Primary Account", find.name().get() );
    }

    private void initData()
    {
        account = newAccount( "Primary Account", "primary" );
        account.staffs().addAll( getStaffs() );

        customers = getCustomers();

        account.customers().addAll( customers );
    }
}
