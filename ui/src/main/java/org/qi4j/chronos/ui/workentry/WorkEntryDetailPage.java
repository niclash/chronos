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

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextArea;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.util.DateUtil;

public abstract class WorkEntryDetailPage extends LeftMenuNavPage
{
    private BasePage returnPage;

    public WorkEntryDetailPage( BasePage returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new WorkEntryDetailForm( "workEntryDetailForm" ) );
    }

    private class WorkEntryDetailForm extends Form
    {
        private Button submitButton;

        private SimpleTextField titleTextField;
        private SimpleTextArea descriptionTextArea;

        private SimpleTextField createdDateField;

        private SimpleTextField startTimeFieid;
        private SimpleTextField endTimeField;

        private SimpleTextField durationField;

        public WorkEntryDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            WorkEntryEntityComposite workEntry = getWorkEntry();

            titleTextField = new SimpleTextField( "titleField", workEntry.getTitle() );
            descriptionTextArea = new SimpleTextArea( "descriptionTextArea", workEntry.getDescription() );

            createdDateField = new SimpleTextField( "createDateField", DateUtil.formatDateTime( workEntry.getCreatedDate() ) );

            startTimeFieid = new SimpleTextField( "startTimeField", DateUtil.formatDateTime( workEntry.getStartTime() ) );
            endTimeField = new SimpleTextField( "endTimeField", DateUtil.formatDateTime( workEntry.getEndTime() ) );

            durationField = new SimpleTextField( "durationField", "00:08:33" );//TODO fix me

            submitButton = new Button( "submitButton", new Model( "Return" ) );

            add( titleTextField );
            add( descriptionTextArea );

            add( createdDateField );
            add( startTimeFieid );
            add( endTimeField );

            add( durationField );
            add( submitButton );
        }

        protected void delegateSubmit( IFormSubmittingComponent submittingButton )
        {
            if( submittingButton == submitButton )
            {
                setResponsePage( returnPage );
            }
            else
            {
                throw new IllegalArgumentException( submittingButton + " not handled yet." );
            }
        }
    }

    public abstract WorkEntryEntityComposite getWorkEntry();
}
