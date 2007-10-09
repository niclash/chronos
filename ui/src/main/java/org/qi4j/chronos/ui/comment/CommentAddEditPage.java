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
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.ui.ChronosSession;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextArea;
import org.qi4j.chronos.ui.common.SimpleTextField;

public abstract class CommentAddEditPage extends AddEditBasePage
{
    private MaxLengthTextArea commentTextArea;
    private SimpleTextField userField;

    public CommentAddEditPage( Page goBackPage )
    {
        super( goBackPage );
    }

    public void initComponent( Form form )
    {
        commentTextArea = new MaxLengthTextArea( "commentTextArea", "Comment", Comment.COMMENT_LEN );

        userField = new SimpleTextField( "userField", getUser().getFirstName() + " " + getUser().getLastName(), true );

        form.add( commentTextArea );
        form.add( userField );
    }

    protected void assignFieldValueToComment( CommentComposite commentComposite )
    {
        commentComposite.setText( commentTextArea.getText() );
        commentComposite.setUser( getUser() );
    }

    protected void assignCommentToFieldValue( CommentComposite commentComposite )
    {
        commentTextArea.setText( commentComposite.getText() );
        userField.setText( getUser().getFirstName() + " " + getUser().getLastName() );
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( commentTextArea.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public User getUser()
    {
        return ChronosSession.get().getUser();
    }

    public abstract void onSubmitting();
}
