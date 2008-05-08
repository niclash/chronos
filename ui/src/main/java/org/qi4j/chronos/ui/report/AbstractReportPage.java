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
package org.qi4j.chronos.ui.report;

import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Report;
import org.qi4j.chronos.model.ReportSummary;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.ReportDetail;
import org.qi4j.chronos.model.AccountReport;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.ReportEntityComposite;
import org.qi4j.chronos.model.composites.ReportSummaryEntityComposite;
import org.qi4j.chronos.model.composites.ReportDetailEntityComposite;
import org.qi4j.chronos.model.composites.AccountReportEntityComposite;
import org.qi4j.chronos.model.associations.HasTasks;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.Identity;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.joda.time.Period;
import java.util.Date;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class AbstractReportPage extends LeftMenuNavPage
{
    protected UnitOfWork getUnitOfWork()
    {
        UnitOfWorkFactory factory = ChronosSession.get().getUnitOfWorkFactory();

        if( null == factory.currentUnitOfWork() || !factory.currentUnitOfWork().isOpen() )
        {
            return factory.newUnitOfWork();
        }
        else
        {
            return factory.currentUnitOfWork();
        }
    }

    protected static Report generateReport( UnitOfWork unitOfWork, String name,
                                            HasTasks hasTasks, TimeRangeModel model )
    {
        final Date filterStartDate = model.getStartDate();
        final Date filterEndDate = model.getEndDate();

        Report report = unitOfWork.newEntityBuilder( ReportEntityComposite.class ).newInstance();
        report.startTime().set( filterStartDate );
        report.endTime().set( filterEndDate );
        report.name().set( name );

        ReportSummary summary = unitOfWork.newEntityBuilder( ReportSummaryEntityComposite.class ).newInstance();
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
                            newEntityBuilder( ReportDetailEntityComposite.class ).newInstance();
                        reportDetail.staff().set( staff );
                        reportDetail.workEntries().add( workEntry );
                        summary.reportDetails().add( reportDetail );
                    }
                }
            }
        }

        return report;
    }

    protected static Period calculatePeriod( HasWorkEntries hasWorkEntries )
    {
        long duration = 0L;
        for( WorkEntry workEntry : hasWorkEntries.workEntries() )
        {
            duration += getTimeInMillis( workEntry );
        }

        return new Period( duration );
    }

    protected static Period getPeriod( WorkEntry workEntry )
    {
        return new Period( getTimeInMillis( workEntry ) );
    }

    protected static long getTimeInMillis( WorkEntry workEntry )
    {
        return workEntry.endTime().get().getTime() - workEntry.startTime().get().getTime();
    }

    protected static AccountReport getAccountReport( final UnitOfWork unitOfWork, final Account account )
    {
        final String accountId = ( (Identity) account ).identity().get();
        try
        {
            return unitOfWork.find( accountId,
                                    AccountReportEntityComposite.class );
        }
        catch( EntityCompositeNotFoundException ecnfe )
        {
            System.err.println( "Error: " + ecnfe.getLocalizedMessage() );
            ecnfe.printStackTrace();

            return unitOfWork.
                newEntityBuilder( accountId, AccountReportEntityComposite.class ).newInstance();
        }
    }
}