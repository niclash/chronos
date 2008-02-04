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
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.ui.ChronosSession;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommentAddPage extends CommentAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CommentAddPage.class );

    public CommentAddPage( Page basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
        CommentComposite comment = ChronosWebApp.newInstance( CommentComposite.class );

        try
        {
            assignFieldValueToComment( comment );

            comment.createdDate().set( new Date() );

            addComment( comment );

            logInfoMsg( "Comment is added successfully" );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Comment";
    }

    public User getCommentOwner()
    {
        return ChronosSession.get().getUser();
    }

    public abstract void addComment( CommentComposite comment );
}
