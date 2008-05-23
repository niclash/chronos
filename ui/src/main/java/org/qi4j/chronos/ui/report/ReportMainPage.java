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
package org.qi4j.chronos.ui.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.AccountReport;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Report;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.common.NameChoiceRenderer;
import org.qi4j.chronos.ui.common.SimpleDateField;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.ReportUtil;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public class ReportMainPage extends AbstractReportPage
{
    public ReportMainPage()
    {
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ReportForm( "reportForm", new TimeRangeModel() ) );
    }

    private class ReportForm extends Form
    {
        private final SimpleDateField startTimeDateField;
        private final SimpleDateField endTimeDateField;
        private final DropDownChoice projectDropDownChoice;

        public ReportForm( String id, TimeRangeModel reportmodel )
        {
            super( id );

            CompoundPropertyModel compoundPropertyModel = new CompoundPropertyModel( reportmodel );
            setModel( compoundPropertyModel );

            startTimeDateField = new SimpleDateField( "startTimeDateField" );
            startTimeDateField.setModel( compoundPropertyModel.bind( "startDate" ) );
            endTimeDateField = new SimpleDateField( "endTimeDateField" );
            endTimeDateField.setModel( compoundPropertyModel.bind( "endDate" ) );

            NameChoiceRenderer nameRenderer = new NameChoiceRenderer();
            Project[] projectsArray = new Project[getAccount().projects().size()];
            projectDropDownChoice =
                new DropDownChoice( "projectDropDownChoice",
                                    new CompoundPropertyModel( getAccount().projects().iterator().next() ),
                                    Arrays.asList( getAccount().projects().toArray( projectsArray ) ),
                                    nameRenderer );
            add( projectDropDownChoice );
            add( startTimeDateField );
            add( endTimeDateField );
//            add( new EmptyPanel ("historyPanel" ) );

            SimplePanel historyPanel = new SimplePanel ("historyPanel" );
            historyPanel.setHeaders( "Reports" );
            add( historyPanel );

            UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();

            List<String[]> entries = new ArrayList<String[]>();

            final Account account = unitOfWork.dereference( getAccount() );
            final AccountReport accountReport = ReportUtil.getAccountReport( unitOfWork, account );

            for( Report report : accountReport.reports() )
            {
                entries.add( new String[] { report.name().get() } );
            }
            historyPanel.setEntries( entries );
            Link hidden = new Link( "showDetail")
            {
                public final void onClick()
                {
                }
            };
            hidden.add( new Label( "linkLabel", "" ) );
            hidden.setVisible( false );
            historyPanel.add( hidden );
            
            historyPanel.initComponents();
        }

        protected void onSubmit()
        {
            UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();

            final Project project = unitOfWork.dereference( (Project) projectDropDownChoice.getModelObject() );
            final TimeRangeModel timeRangeModel = (TimeRangeModel) getModelObject();
            final Date startTime = timeRangeModel.getStartDate();
            final Date endTime = timeRangeModel.getEndDate();

            Report report = ReportUtil.generateReport( unitOfWork, "test", project, startTime, endTime );

            final Account account = unitOfWork.dereference( getAccount() );
            final String reportId = ( (Identity) report).identity().get();

            final AccountReport accountReport = ReportUtil.getAccountReport( unitOfWork, account );
            accountReport.account().set( account );
            accountReport.reports().add( report );

            try
            {
                ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            }
            catch( UnitOfWorkCompletionException uowce )
            {
                System.err.println( "Error: " + uowce.getLocalizedMessage() );
                uowce.printStackTrace();
            }

            setResponsePage( new ReportSummaryPage( ReportMainPage.this, reportId ) );
        }
    }
}
