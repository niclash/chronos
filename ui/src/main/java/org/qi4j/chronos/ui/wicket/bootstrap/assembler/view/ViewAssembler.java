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
package org.qi4j.chronos.ui.wicket.bootstrap.assembler.view;

import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.ui.contactperson.ContactPersonHomePage;
import org.qi4j.chronos.ui.customer.CustomerListPage;
import org.qi4j.chronos.ui.pricerate.PriceRateScheduleListPage;
import org.qi4j.chronos.ui.project.ProjectAddPage;
import org.qi4j.chronos.ui.project.ProjectListPage;
import org.qi4j.chronos.ui.projectrole.ProjectRoleListPage;
import org.qi4j.chronos.ui.report.DomainObjectModel;
import org.qi4j.chronos.ui.report.ReportMainPage;
import org.qi4j.chronos.ui.staff.StaffHomePage;
import org.qi4j.chronos.ui.staff.StaffListPage;
import org.qi4j.chronos.ui.systemrole.SystemRoleListPage;
import org.qi4j.chronos.ui.wicket.admin.account.AccountAddPage;
import org.qi4j.chronos.ui.wicket.admin.account.AccountDetailPage;
import org.qi4j.chronos.ui.wicket.admin.account.AccountEditPage;
import static org.qi4j.structure.Visibility.layer;

/**
 * TODO: Remove this
 */
public class ViewAssembler
    implements Assembler
{
    public void assemble( ModuleAssembly module )
        throws AssemblyException
    {
        module.addObjects(
            StaffHomePage.class,
            ContactPersonHomePage.class,
            AccountAddPage.class,
            AccountDetailPage.class,
            AccountEditPage.class,
            SystemRoleListPage.class,
            PriceRateScheduleListPage.class,
            CustomerListPage.class,
            StaffListPage.class,
            ProjectRoleListPage.class,
            ProjectListPage.class,
            ProjectAddPage.class,
            ReportMainPage.class,
            DomainObjectModel.class

//            TaskDetailPage.class,
//            CommentDetailPage.class,

        ).visibleIn( layer );
    }
}