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
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.ui.common.SimpleTextArea;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.util.DateUtil;

public abstract class CommentDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public CommentDetailPage( Page returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new CommentDetailForm( "commentDetailForm" ) );
    }

    private class CommentDetailForm extends Form
    {
        private Button submitButton;

        private SimpleTextField createdDateField;
        private SimpleTextField userField;
        private SimpleTextArea commentTextArea;

        public CommentDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            Comment comment = getComment();

            String createdDateString = DateUtil.formatDateTime( comment.createdDate().get() );
            createdDateField = new SimpleTextField( "createdDateField", createdDateString );
            userField = new SimpleTextField( "userField", comment.user().get().fullName().get() );

            commentTextArea = new SimpleTextArea( "commentTextArea", comment.text().get() );

            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            add( createdDateField );
            add( userField );
            add( commentTextArea );
            add( submitButton );
        }
    }

    public abstract Comment getComment();
}
