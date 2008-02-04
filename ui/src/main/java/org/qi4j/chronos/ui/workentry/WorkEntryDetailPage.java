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
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.ui.comment.CommentTab;
import org.qi4j.chronos.ui.common.SimpleTextArea;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.util.DateUtil;

public abstract class WorkEntryDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public WorkEntryDetailPage( Page returnPage )
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

        private TabbedPanel tabbedPanel;

        public WorkEntryDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            WorkEntryEntityComposite workEntry = getWorkEntry();

            titleTextField = new SimpleTextField( "titleField", workEntry.title().get() );
            descriptionTextArea = new SimpleTextArea( "descriptionTextArea", workEntry.description().get() );

            createdDateField = new SimpleTextField( "createDateField", DateUtil.formatDateTime( workEntry.createdDate().get() ) );

            startTimeFieid = new SimpleTextField( "startTimeField", DateUtil.formatDateTime( workEntry.startTime().get() ) );
            endTimeField = new SimpleTextField( "endTimeField", DateUtil.formatDateTime( workEntry.endTime().get() ) );

            durationField = new SimpleTextField( "durationField", "00:08:33" );//TODO fix me

            List<AbstractTab> tabs = new ArrayList<AbstractTab>();

            tabs.add( createCommentTab() );

            tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            submitButton = new Button( "submitButton", new Model( "Return" ) );

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
            return new CommentTab( "Comment" )
            {
                public HasComments getHasComments()
                {
                    return WorkEntryDetailPage.this.getWorkEntry();
                }

                public void addComment( CommentComposite comment )
                {
                    WorkEntryDetailPage.this.addComment( comment );
                }
            };
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

    private void addComment( CommentComposite comment )
    {
        WorkEntryEntityComposite workEntry = getWorkEntry();

        workEntry.comments().add( comment );

        getServices().getWorkEntryService().update( workEntry );
    }

    public abstract WorkEntryEntityComposite getWorkEntry();
}
