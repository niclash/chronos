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
import java.util.List;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommentTable extends ActionTable<Comment>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( CommentTable.class );

    private static final String[] TABLE_NAMES = { "User", "Created Date", "Comment" };

    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_FAIL = "deleteFailed";

    private static final long serialVersionUID = 1L;

    public CommentTable( String id, IModel<? extends HasComments> model, CommentDataProvider commentDataProvider )
    {
        super( id, model, commentDataProvider, TABLE_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction( newDeleteAction() );
    }

    private DeleteAction newDeleteAction()
    {
        return new DeleteAction<Comment>( getString( DELETE_ACTION ) )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Comment> comments )
            {
                handleDeleteAction( comments );

                info( getString( DELETE_SUCCESS ) );
            }
        };
    }

    private void handleDeleteAction( List<Comment> comments )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            for( Comment comment : comments )
            {
                HasComments hasComments = (HasComments) getDefaultModel().getObject();

                hasComments.comments().remove( comment );
                unitOfWork.remove( comment );
            }

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL ) );

            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }


    private SimpleLink createDetailLink( String id, String text, final IModel<Comment> model )
    {
        return new SimpleLink( id, text )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                CommentDetailPage detailPage = new CommentDetailPage( this.getPage(), model );
                setResponsePage( detailPage );
            }
        };
    }

    public void populateItems( Item<Comment> commentItem )
    {
        IModel<Comment> commentModel = commentItem.getModel();

        Comment comment = commentModel.getObject();
        User user = comment.user().get();

        Date createdDate = comment.createdDate().get();
        commentItem.add( createDetailLink( "user", user.fullName().get(), commentModel ) );
        commentItem.add( createDetailLink( "createdDate", DateUtil.formatDateTime( createdDate ), commentModel ) );
        commentItem.add( createDetailLink( "comment", comment.text().get(), commentModel ) );
    }
}
