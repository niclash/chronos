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
import org.qi4j.chronos.model.composites.AccountEntity;
import org.qi4j.chronos.model.composites.AccountReportEntity;
import org.qi4j.chronos.model.composites.AddressEntity;
import org.qi4j.chronos.model.composites.AdminEntity;
import org.qi4j.chronos.model.composites.CityEntity;
import org.qi4j.chronos.model.composites.CommentEntity;
import org.qi4j.chronos.model.composites.ContactEntity;
import org.qi4j.chronos.model.composites.ContactPersonEntity;
import org.qi4j.chronos.model.composites.CountryEntity;
import org.qi4j.chronos.model.composites.CustomerEntity;
import org.qi4j.chronos.model.composites.LegalConditionEntity;
import org.qi4j.chronos.model.composites.LoginEntity;
import org.qi4j.chronos.model.composites.MoneyEntity;
import org.qi4j.chronos.model.composites.OngoinWorkEntryEntity;
import org.qi4j.chronos.model.composites.PriceRateEntity;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntity;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntity;
import org.qi4j.chronos.model.composites.ProjectEntity;
import org.qi4j.chronos.model.composites.ProjectRoleEntity;
import org.qi4j.chronos.model.composites.RelationshipEntity;
import org.qi4j.chronos.model.composites.ReportDetailEntity;
import org.qi4j.chronos.model.composites.ReportEntity;
import org.qi4j.chronos.model.composites.ReportSummaryEntity;
import org.qi4j.chronos.model.composites.StaffEntity;
import org.qi4j.chronos.model.composites.StateEntity;
import org.qi4j.chronos.model.composites.SystemRoleEntity;
import org.qi4j.chronos.model.composites.TaskEntity;
import org.qi4j.chronos.model.composites.TimeRangeEntity;
import org.qi4j.chronos.model.composites.WorkEntryEntity;
import static org.qi4j.structure.Visibility.application;

public class DomainAssembler
    implements Assembler
{
    public void assemble( ModuleAssembly aModule )
        throws AssemblyException
    {
        aModule.addEntities(
            AdminEntity.class,
            ContactPersonEntity.class,
            LoginEntity.class,
            AddressEntity.class,
            CityEntity.class,
            StateEntity.class,
            CountryEntity.class,
            MoneyEntity.class,
            StaffEntity.class,
            SystemRoleEntity.class,
            ProjectRoleEntity.class,
            PriceRateEntity.class,
            PriceRateScheduleEntity.class,
            CustomerEntity.class,
            ContactEntity.class,
            RelationshipEntity.class,
            ProjectEntity.class,
            TaskEntity.class,
            ProjectAssigneeEntity.class,
            TimeRangeEntity.class,
            AccountEntity.class,
            LegalConditionEntity.class,
            OngoinWorkEntryEntity.class,
            WorkEntryEntity.class,
            CommentEntity.class,
            ReportEntity.class,
            ReportDetailEntity.class,
            ReportSummaryEntity.class,
            AccountReportEntity.class
        ).visibleIn( application );
    }
}
