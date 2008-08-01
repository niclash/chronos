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

import java.util.Date;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public class CommentDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    public CommentDetailPage( final Page basePage, IModel<Comment> comment )
    {
        ChronosCompoundPropertyModel<Comment> model = new ChronosCompoundPropertyModel<Comment>( comment.getObject() );
        setDefaultModel( model );

        add( new FeedbackPanel( "feedbackPanel" ) );

        TextField<Date> createdDateField = new TextField<Date>( "createdDate", model.<Date>bind( "createdDate" ) );
        TextField<String> userField = new TextField<String>( "user", model.<String>bind( "user.fullName" ) );
        TextArea<String> commentTextArea = new TextArea<String>( "text", model.<String>bind( "text" ) );

        final Button submitButton = new Button( "submitButton", new Model<String>( "Return" ) )
        {
            private static final long serialVersionUID = 1L;

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
