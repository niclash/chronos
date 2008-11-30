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
package org.qi4j.chronos.util;

import java.util.Date;
import org.qi4j.chronos.domain.model.AccountReport;
import org.qi4j.chronos.domain.model.Report;
import org.qi4j.chronos.domain.model.ReportDetail;
import org.qi4j.chronos.domain.model.ReportSummary;
import org.qi4j.chronos.domain.model.Task;
import org.qi4j.chronos.domain.model.WorkEntry;
import org.qi4j.chronos.domain.model.account.Account;
import org.qi4j.chronos.domain.model.associations.HasTasks;
import org.qi4j.chronos.domain.model.common.period.PeriodState;
import org.qi4j.chronos.domain.model.user.Staff;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;

public final class ReportUtil
{
    public static Report generateReport( UnitOfWork unitOfWork, String name, HasTasks hasTasks,
                                         Date filterStartDate, Date filterEndDate )
    {
        EntityBuilder<Report> builder = unitOfWork.newEntityBuilder( Report.class );
        PeriodState periodState = builder.stateFor( PeriodState.class );
        periodState.startTime().set( filterStartDate );
        periodState.endTime().set( filterEndDate );
        Report report = builder.newInstance();
//        report.name().set( name );

        ReportSummary summary = unitOfWork.newEntityBuilder( ReportSummary.class ).newInstance();
        report.reportSummary().set( summary );

        for( Task task : hasTasks.tasks() )
        {
            for( WorkEntry workEntry : task.workEntries() )
            {
                Date startDate = workEntry.startTime();
                Date endDate = workEntry.endTime();

                if( startDate.after( filterStartDate ) && endDate.before( filterEndDate ) )
                {
                    Staff staff = workEntry.projectAssignee().get().staff().get();
                    boolean exists = false;
                    for( ReportDetail reportDetail : summary.reportDetails() )
                    {
                        if( reportDetail.staff().get().equals( staff ) )
                        {
                            exists = true;
                            reportDetail.workEntries().add( workEntry );
                        }
                    }

                    if( !exists )
                    {
//                        ReportDetail reportDetail = unitOfWork.
//                            newEntityBuilder( ReportDetailEntity.class ).newInstance();
//                        reportDetail.staff().set( staff );
//                        reportDetail.workEntries().add( workEntry );
//                        summary.reportDetails().add( reportDetail );
                    }
                }
            }
        }

        return report;
    }

    public static AccountReport getAccountReport( final UnitOfWork unitOfWork, final Account account )
    {
        final String accountId = ( (Identity) account ).identity().get();
        try
        {
            return unitOfWork.find( accountId, AccountReport.class );
        }
        catch( EntityCompositeNotFoundException ecnfe )
        {
            // expected
        }
        return unitOfWork.newEntityBuilder( accountId, AccountReport.class ).newInstance();
    }
}
