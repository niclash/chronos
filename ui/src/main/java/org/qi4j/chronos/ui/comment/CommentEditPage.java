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

import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommentEditPage extends CommentAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CommentEditPage.class );

    public CommentEditPage( BasePage basePage )
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
        return "Edit";
    }

    public String getTitleLabel()
    {
        return "Edit Comment";
    }

    public void onSubmitting()
    {
        CommentComposite comment = getComment();

        try
        {
            assignFieldValueToComment( comment );

            logInfoMsg( "Comment is updated successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public abstract CommentComposite getComment();
}