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

import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.service.CommentService;
import org.qi4j.entity.Identity;

public abstract class CommentEditDialog extends CommentAddEditDialog
{
    public CommentEditDialog()
    {
        assignCommentToFieldValue( getComment() );
    }

    public void handleOkClicked()
    {
        CommentComposite comment = getComment();

        CommentService commentService = getServices().getCommentService();

        //TODO bp, different from web app? because getComment always return same instance?
        String ownerId = ( (Identity) comment.getUser() ).getIdentity();

        CommentComposite oldComment = commentService.get( getHasComments(), comment.getCreatedDate(), ownerId );

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

    public User getCommentOwner()
    {
        return getComment().getUser();
    }

    public abstract CommentComposite getComment();

    public abstract HasComments getHasComments();
}
