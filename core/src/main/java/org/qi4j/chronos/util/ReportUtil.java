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
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.AccountReport;
import org.qi4j.chronos.model.Report;
import org.qi4j.chronos.model.ReportDetail;
import org.qi4j.chronos.model.ReportSummary;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasTasks;
import org.qi4j.chronos.model.composites.AccountReportEntity;
import org.qi4j.chronos.model.composites.ReportDetailEntity;
import org.qi4j.chronos.model.composites.ReportEntity;
import org.qi4j.chronos.model.composites.ReportSummaryEntity;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;

public final class ReportUtil
{
    public static Report generateReport( UnitOfWork unitOfWork, String name, HasTasks hasTasks,
                                         Date filterStartDate, Date filterEndDate )
    {
        Report report = unitOfWork.newEntityBuilder( ReportEntity.class ).newInstance();
        report.startTime().set( filterStartDate );
        report.endTime().set( filterEndDate );
        report.name().set( name );

        ReportSummary summary = unitOfWork.newEntityBuilder( ReportSummaryEntity.class ).newInstance();
        report.reportSummary().set( summary );

        for( Task task : hasTasks.tasks() )
        {
            for( WorkEntry workEntry : task.workEntries() )
            {
                Date startDate = workEntry.startTime().get();
                Date endDate = workEntry.endTime().get();

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
                        ReportDetail reportDetail = unitOfWork.
                            newEntityBuilder( ReportDetailEntity.class ).newInstance();
                        reportDetail.staff().set( staff );
                        reportDetail.workEntries().add( workEntry );
                        summary.reportDetails().add( reportDetail );
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
            return unitOfWork.find( accountId,
                                    AccountReportEntity.class );
        }
        catch( EntityCompositeNotFoundException ecnfe )
        {
            // expected
        }
        return unitOfWork.
                newEntityBuilder( accountId, AccountReportEntity.class ).newInstance();
    }
}
