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
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.util.ChronosUtil;

public abstract class CommentAddDialog extends CommentAddEditDialog
{
    public CommentAddDialog()
    {
    }

    public String getOkButtonText()
    {
        return "Add";
    }

    public User getCommentOwner()
    {
        return getChronosSetting().getStaff();
    }

    public void handleOkClicked()
    {
        CommentComposite comment = getChronosSetting().newInstance( CommentComposite.class );

        //set created date
        comment.setCreatedDate( ChronosUtil.getCurrentDate() );

        //set values
        assignFieldValueToComment( comment );

        //adding comment
        addingComment( comment );
    }

    public String getDialogTitle()
    {
        return "Add Comment";
    }

    public abstract void addingComment( CommentComposite comment );
}
