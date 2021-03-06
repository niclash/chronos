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
package org.qi4j.chronos.comment;

import com.intellij.openapi.project.Project;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.service.CommentService;

public abstract class CommentEditDialog extends CommentAddEditDialog
{
    public CommentEditDialog( Project project )
    {
        super( project );
        assignCommentToFieldValue( getComment() );
    }

    public void handleOkClicked()
    {
        Comment comment = getComment();

        CommentService commentService = getServices().getCommentService();

        //TODO bp, different from web app? because getComment always return same instance?
        String ownerId = comment.user().get().identity().get();

        Comment oldComment = commentService.get( getHasComments(), comment.createdDate().get(), ownerId );

        //set values
        assignFieldValueToComment( comment );

        //update comment
        getServices().getCommentService().update( getHasComments(), oldComment, comment );
    }

    public String getOkButtonText()
    {
        return "Save";
    }

    public String getDialogTitle()
    {
        return "Edit Comment";
    }

    public User getCreatedBy()
    {
        return getComment().user().get();
    }

    public abstract Comment getComment();

    public abstract HasComments getHasComments();
}
