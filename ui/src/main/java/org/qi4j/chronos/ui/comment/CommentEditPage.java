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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentEntityComposite;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.library.framework.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommentEditPage extends CommentAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CommentEditPage.class );
    private static final String UPDATE_FAIL = "updateFailed";
    private static final String UPDATE_SUCCESS = "updateSuccessful";
    private static final String SUBMIT_BUTTON = "editPageSubmitButton";
    private static final String TITLE_LABEL = "editPageTitleLabel";

    public CommentEditPage( Page basePage, final String commentId )
    {
        super( basePage );

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return getUnitOfWork().find( commentId, CommentEntityComposite.class );
                    }
                }
            )
        );
    }

    public String getSubmitButtonValue()
    {
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }

    public void onSubmitting()
    {
        try
        {
            getUnitOfWork().complete();
            logInfoMsg( getString( UPDATE_SUCCESS ) );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            reset();

            logErrorMsg( getString( UPDATE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
        catch( ValidationException err )
        {
            reset();

            logErrorMsg( getString( UPDATE_FAIL, new Model( err ) ) );
            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }

    public User getCommentOwner()
    {
        return getComment().user().get();
    }

    public abstract HasComments getHasComments();

    public abstract Comment getComment();
}
