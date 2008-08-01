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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public abstract class CommentAddEditPage extends AddEditBasePage<Comment>
{
    private static final long serialVersionUID = 1L;


    public CommentAddEditPage( Page goBackPage, IModel<Comment> commentModel )
    {
        super( goBackPage, commentModel );
    }

    public void initComponent( Form<Comment> form )
    {
        TextArea commentTextArea = new TextArea( "text" );

        ChronosCompoundPropertyModel<Comment> model = (ChronosCompoundPropertyModel<Comment>) form.getModel();

        TextField<String> user = new TextField<String>( "user", model.<String>bind( "user.fullname" ) );

        form.add( commentTextArea );
        form.add( user );
    }

    public void handleSubmitClicked( IModel<Comment> comment )
    {
        onSubmitting( comment );
    }

    public abstract User getCommentOwner();

    public abstract void onSubmitting( IModel<Comment> comment );
}
