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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.Period;
import org.qi4j.chronos.model.Report;
import org.qi4j.chronos.model.ReportDetail;
import org.qi4j.chronos.model.composites.ReportEntityComposite;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;

public class ReportSummaryPage extends AbstractReportPage
{
    private Page goBackPage;

    public ReportSummaryPage( final Page goBackPage, final String reportId )
    {
        this.goBackPage = goBackPage;

        IModel reportModel = new CompoundPropertyModel( new LoadableDetachableModel()
        {
            protected Object load()
            {
                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( reportId, ReportEntityComposite.class );
            }
        } );
        setModel( reportModel );
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ReportSummaryForm( "reportSummaryForm" ) );

/*
        final SimpleLink goBackLink = new SimpleLink( "goBackLink", " Go Back" )
        {
            public void linkClicked()
            {
                setResponsePage( goBackPage );
            }
        };
        add( goBackLink );
*/

        add( new Label( "title", new PropertyModel( getModel(), "displayValue" ) ) );

        Report report = (Report) getModelObject();
//        add( new Label( "title", report.displayValue().get() ) );
        SimplePanel result = new SimplePanel( "summaryPanel" );
        result.setTitle( report.name().get() );
        result.setHeaders( "Staff", "Total Hours" );

        List<String[]> list = new ArrayList<String[]>();
        for( ReportDetail reportDetail : report.reportSummary().get().reportDetails() )
        {
            Period period = calculatePeriod( reportDetail );
            list.add( new String[]{ reportDetail.staff().get().fullName().get(), "" + period.getHours() } );
        }

        final Label label = new Label( "linkLabel", "[ show detail ]" );
        final Link showDetail = new Link( "showDetail" )
        {
            public final void onClick()
            {
//                detailPanel.setVisible( !detailPanel.isVisible() );
                if( label.getModelObjectAsString().equals( "[ show detail ]" ) )
                {
                    label.setModelObject( "[ hide detail ]" );
                }
                else
                {
                    label.setModelObject( "[ show detail ]" );
                }
            }
        };
        showDetail.add( label );
        result.add( showDetail );

        result.setEntries( list );
        result.initComponents();
        addOrReplace( result );
    }

    private class ReportSummaryForm extends Form
    {
        private static final long serialVersionUID = 1L;

        public ReportSummaryForm( String id )
        {
            super( id );

            final Button goButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setRedirect( true );
                }
            };
            add( goButton );

            final Button detailButton = new Button( "detailButton", new Model( "Details" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( goBackPage );
                }
            };
            add( detailButton );
        }
    }
/*

            List<String[]> list = new ArrayList<String[]>();
            for( ReportDetail reportDetail : report.reportSummary().get().reportDetails() )
            {
                Period period = calculatePeriod( reportDetail );
                list.add( new String[] { reportDetail.staff().get().fullName().get(), "" + period.getHours() } );

                final SimplePanel detailPanel = new SimplePanel( "detailPanel" );
                detailPanel.setTitle( reportDetail.staff().get().fullName().get() );
                detailPanel.setHeaders( "Work Entry", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" );

                SortedSet<WorkEntry> set = new TreeSet<WorkEntry>( new TimeRangeComparator() );
                List<String[]> entries = new ArrayList<String[]>();
                for( WorkEntry entry : reportDetail.workEntries() )
                {
                    set.add( entry );
                }

                for( WorkEntry entry : set )
                {
                    String[] temp = { "", "", "", "", "", "", "", "" };
                    temp[0] = entry.title().get() + " " + DateFormat.getInstance().format( entry.startTime().get() );
                    Calendar now = Calendar.getInstance();
                    now.setTime( entry.endTime().get() );
                    int day = now.get( Calendar.DAY_OF_WEEK ) - 1;
                    temp[day == 0 ? 7 : day] = "" + getPeriod( entry ).getHours();
                    entries.add( temp );
                }
                detailPanel.setEntries( entries );
                detailPanel.initComponents();
                addOrReplace( detailPanel );
                detailPanel.setVisible( false );
                Link hidden = new Link( "showDetail")
                {
                    public final void onClick()
                    {
                    }
                };
                hidden.add( new Label( "linkLabel", "" ) );
                hidden.setVisible( false );
                detailPanel.add( hidden );

                final Label label = new Label( "linkLabel", "[ show detail ]" );
                final Link showDetail = new Link( "showDetail" )
                {
                    public final void onClick()
                    {
                        detailPanel.setVisible( !detailPanel.isVisible() );
                        if ( label.getModelObjectAsString().equals( "[ show detail ]" ) )
                        {
                            label.setModelObject( "[ hide detail ]" );
                        }
                        else
                        {
                            label.setModelObject( "[ show detail ]" );
                        }
                    }
                };
                showDetail.add( label );
                result.add( showDetail );
            }
            result.setEntries( list );
            result.initComponents();
            addOrReplace( result );
*/
}
