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
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommentAddPage extends CommentAddEditPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( CommentAddPage.class );

    public CommentAddPage( Page basePage, IModel<Comment> comment )
    {
        super( basePage, comment );
    }

//    private void bindModel()
//    {
//        setModel(
//            new CompoundPropertyModel(
//                new LoadableDetachableModel()
//                {
//                    public Object load()
//                    {
//                        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
//
//                        final Comment comment =
//                            unitOfWork.newEntityBuilder( CommentEntityComposite.class ).newInstance();
//                        final User user = unitOfWork.dereference( getCommentOwner() );
//                        comment.createdDate().set( new Date() );
//                        comment.user().set( user );
//
//                        return comment;
//                    }
//                }
//            )
//        );
//    }

    public void onSubmitting( IModel<Comment> model )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            final Comment comment = model.getObject();
            final HasComments hasComments = unitOfWork.dereference( getHasComments() );
            hasComments.comments().add( comment );

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

            logInfoMsg( "Comment was added successfully." );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( "Fail to add new comment" );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "New Comment";
    }

    public User getCommentOwner()
    {
        return ChronosSession.get().getUser();
    }

    public abstract HasComments getHasComments();
}
