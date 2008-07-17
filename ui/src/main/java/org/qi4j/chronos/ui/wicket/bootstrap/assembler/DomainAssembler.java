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
package org.qi4j.chronos.ui.wicket.bootstrap.assembler;

import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AccountReportEntityComposite;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.CommentEntityComposite;
import org.qi4j.chronos.model.composites.ContactEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.LegalConditionEntityComposite;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.MoneyEntityComposite;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntityComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipEntityComposite;
import org.qi4j.chronos.model.composites.ReportDetailEntityComposite;
import org.qi4j.chronos.model.composites.ReportEntityComposite;
import org.qi4j.chronos.model.composites.ReportSummaryEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import static org.qi4j.structure.Visibility.application;

public class DomainAssembler implements Assembler
{
    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        module.addComposites(
            AdminEntityComposite.class,
            ContactPersonEntityComposite.class,
            LoginEntityComposite.class,
            AddressEntityComposite.class,
            CityEntityComposite.class,
            StateEntityComposite.class,
            CountryEntityComposite.class,
            MoneyEntityComposite.class,
            StaffEntityComposite.class,
            SystemRoleEntityComposite.class,
            ProjectRoleEntityComposite.class,
            PriceRateEntityComposite.class,
            PriceRateScheduleEntityComposite.class,
            CustomerEntityComposite.class,
            ContactPersonEntityComposite.class,
            ContactEntityComposite.class,
            RelationshipEntityComposite.class,
            ProjectEntityComposite.class,
            TaskEntityComposite.class,
            ProjectAssigneeEntityComposite.class,
            TimeRangeEntityComposite.class,
            AccountEntityComposite.class,
            LegalConditionEntityComposite.class,
            OngoingWorkEntryEntityComposite.class,
            WorkEntryEntityComposite.class,
            CommentEntityComposite.class,
            ReportEntityComposite.class,
            ReportDetailEntityComposite.class,
            ReportSummaryEntityComposite.class,
            AccountReportEntityComposite.class
        ).visibleIn( application );
    }
}
