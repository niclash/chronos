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
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.service.CommentService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommentEditPage extends CommentAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CommentEditPage.class );

    public CommentEditPage( Page basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        CommentComposite comment = getComment();

        assignCommentToFieldValue( comment );
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Comment";
    }

    private CommentService getCommentService()
    {
        return ChronosWebApp.getServices().getCommentService();
    }

    public void onSubmitting()
    {
        try
        {
            CommentComposite toBeUpdated = getComment();
            CommentComposite oldComment = getComment();

            assignFieldValueToComment( toBeUpdated );

            getCommentService().update( getHasComments(), oldComment, toBeUpdated );

            logInfoMsg( "Comment is updated successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public User getCommentOwner()
    {
        return getComment().user().get();
    }

    public abstract HasComments getHasComments();

    public abstract CommentComposite getComment();

}
