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
package org.qi4j.chronos.ui.comment;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.composites.CommentEntityComposite;
import org.qi4j.chronos.ui.common.SimpleTextArea;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.composite.scope.Uses;

public class CommentDetailPage extends LeftMenuNavPage
{
    private final Page basePage;

    public CommentDetailPage( @Uses Page basePage, final @Uses String commentId )
    {
        this.basePage = basePage;

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( commentId, CommentEntityComposite.class );
                    }
                }
            )
        );
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new CommentDetailForm( "commentDetailForm", getModel() ) );
    }

    private class CommentDetailForm extends Form
    {
        public CommentDetailForm( String id, final IModel iModel )
        {
            super( id );

            initComponents( iModel );
        }

        private void initComponents( IModel iModel )
        {
            final Comment comment = (Comment) iModel.getObject();
            final String createdDateString = DateUtil.formatDateTime( comment.createdDate().get() );
            final SimpleTextField createdDateField = new SimpleTextField( "createdDateField", createdDateString );
            final SimpleTextField userField = new SimpleTextField( "userField", comment.user().get().fullName().get() );
            final SimpleTextArea commentTextArea = new SimpleTextArea( "commentTextArea", comment.text().get() );
            final Button submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( basePage );
                }
            };

            add( createdDateField );
            add( userField );
            add( commentTextArea );
            add( submitButton );
        }
    }
}
