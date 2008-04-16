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
package org.qi4j.chronos.workentry;

import com.intellij.openapi.project.Project;
import org.qi4j.chronos.comment.CommentAddDialog;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.util.UiUtil;

public abstract class WorkEntryCommentAddDialog extends CommentAddDialog
{
    public WorkEntryCommentAddDialog( Project project )
    {
        super( project );
    }

    public void addingComment( Comment comment )
    {
        WorkEntry workEntry = getWorkEntry();

        //add comment to workEntry
        workEntry.comments().add( comment );

        UiUtil.showMsgDialog( "Comment added", "New comment is added successfully." );

        //update task
        getServices().getWorkEntryService().update( workEntry );
    }

    public abstract WorkEntry getWorkEntry();
}
