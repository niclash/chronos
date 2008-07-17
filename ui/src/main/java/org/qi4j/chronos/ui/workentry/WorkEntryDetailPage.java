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
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.WorkEntryEntity;
import org.qi4j.chronos.ui.comment.CommentTab;
import org.qi4j.chronos.ui.common.SimpleTextArea;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.report.AbstractReportPage;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.entity.Identity;

public class WorkEntryDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public WorkEntryDetailPage( Page returnPage, final String workEntryId )
    {
        this.returnPage = returnPage;

        setDefaultModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( workEntryId, WorkEntryEntity.class );
                    }
                }
            )
        );
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new WorkEntryDetailForm( "workEntryDetailForm", getDefaultModel() ) );
    }

    private class WorkEntryDetailForm extends Form
    {
        public WorkEntryDetailForm( String id, IModel iModel )
        {
            super( id );

            initComponents( iModel );
        }

        private void initComponents( IModel imodel )
        {
            final WorkEntry workEntry = (WorkEntry) imodel.getObject();
            final SimpleTextField titleTextField = new SimpleTextField( "titleField", workEntry.title().get() );
            final SimpleTextArea descriptionTextArea =
                new SimpleTextArea( "descriptionTextArea", workEntry.description().get() );
            final SimpleTextField createdDateField =
                new SimpleTextField( "createDateField", DateUtil.formatDateTime( workEntry.createdDate().get() ) );
            final SimpleTextField startTimeFieid =
                new SimpleTextField( "startTimeField", DateUtil.formatDateTime( workEntry.startTime().get() ) );
            final SimpleTextField endTimeField =
                new SimpleTextField( "endTimeField", DateUtil.formatDateTime( workEntry.endTime().get() ) );
            final SimpleTextField durationField =
                new SimpleTextField( "durationField", AbstractReportPage.getPeriod( workEntry ).toString() );

            List<ITab> tabs = new ArrayList<ITab>();
            tabs.add( createCommentTab() );

            final TabbedPanel tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );
            final Button submitButton =
                new Button( "submitButton", new Model( "Return" ) )
                {
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
            return new CommentTab( "Comment" )
            {
                public List<String> dataList( int first, int count )
                {
                    List<String> commentIdList = new ArrayList<String>();
                    for( Comment comment : WorkEntryDetailPage.this.getHasComments().comments() )
                    {
                        commentIdList.add( ( (Identity) comment ).identity().get() );
                    }
                    return commentIdList.subList( first, first + count );
                }

                public HasComments getHasComments()
                {
                    return WorkEntryDetailPage.this.getHasComments();
                }
            };
        }
    }

    protected HasComments getHasComments()
    {
        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( (WorkEntry) getDefaultModelObject() );
    }
}
