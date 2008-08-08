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
package org.qi4j.chronos.ui.workentry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.ui.comment.CommentTab;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class WorkEntryDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private Page returnPage;
    private static final String WICKET_ID_FEEDBACK_PANEL = "feedbackPanel";
    private static final String WICKET_ID_WORK_ENTRY_DETAIL_FORM = "workEntryDetailForm";

    public WorkEntryDetailPage( Page returnPage, IModel<WorkEntry> workEntryModel )
    {
        this.returnPage = returnPage;

        ChronosCompoundPropertyModel<WorkEntry> model = new ChronosCompoundPropertyModel<WorkEntry>( workEntryModel.getObject() );

        add( new FeedbackPanel( WICKET_ID_FEEDBACK_PANEL ) );
        add( new WorkEntryDetailForm( WICKET_ID_WORK_ENTRY_DETAIL_FORM, model ) );
    }

    private class WorkEntryDetailForm extends Form<WorkEntry>
    {
        private static final long serialVersionUID = 1L;

        public WorkEntryDetailForm( String id, ChronosCompoundPropertyModel<WorkEntry> model )
        {
            super( id );

            setModel( model );

            TextField titleTextField = new TextField<String>( "titleField", model.<String>bind( "title" ) );
            TextArea descriptionTextArea = new TextArea<String>( "descriptionTextArea", model.<String>bind( "description" ) );

            TextField createdDateField = new TextField<Date>( "createDateField", model.<Date>bind( "createdDate" ) );
            TextField startTimeFieid = new TextField<Date>( "startTimeField", model.<Date>bind( "startTime" ) );

            TextField endTimeField = new TextField<Date>( "endTimeField", model.<Date>bind( "endTime" ) );
            TextField durationField = new TextField<Date>( "durationField", model.<Date>bind( "duration" ) );

            List<ITab> tabs = new ArrayList<ITab>();
            tabs.add( createCommentTab() );

            TabbedPanel tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );
            Button submitButton = new Button( "submitButton", new Model<String>( "Return" ) )
            {
                private static final long serialVersionUID = 1L;

                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            add( titleTextField );
            add( descriptionTextArea );
            add( createdDateField );
            add( startTimeFieid );
            add( endTimeField );
            add( durationField );
            add( tabbedPanel );
            add( submitButton );
        }

        private CommentTab createCommentTab()
        {
            ChronosDetachableModel<WorkEntry> workEntry = new ChronosDetachableModel<WorkEntry>( getWorkEntry() );

            return new CommentTab( "Comment", workEntry );
        }
    }

    private WorkEntry getWorkEntry()
    {
        return (WorkEntry) getDefaultModelObject();
    }

    protected HasComments getHasComments()
    {
        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( (WorkEntry) getDefaultModelObject() );
    }
}
