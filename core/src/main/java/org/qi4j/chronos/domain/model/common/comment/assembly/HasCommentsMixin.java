/*  Copyright 2008 Edward Yakop.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
* implied.
*
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.qi4j.chronos.domain.model.common.comment.assembly;

import java.util.Date;
import org.qi4j.chronos.domain.model.common.comment.Comment;
import org.qi4j.chronos.domain.model.common.comment.CommentState;
import org.qi4j.chronos.domain.model.common.comment.association.HasComments;
import org.qi4j.chronos.domain.model.common.comment.association.HasCommentsState;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.entity.EntityBuilder;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public class HasCommentsMixin
    implements HasComments
{
    @This private HasCommentsState state;
    @Structure private UnitOfWorkFactory uowf;
    @Service CommentFactory commentFactory;

    public final Query<Comment> comments()
    {
        UnitOfWork uow = uowf.currentUnitOfWork();
        QueryBuilderFactory qbf = uow.queryBuilderFactory();
        QueryBuilder<Comment> builder = qbf.newQueryBuilder( Comment.class );
        return builder.newQuery( state.comments() );
    }

    public final Comment addComment( String commentContent, User user )
    {
        Comment comment = create( commentContent, user );
        state.comments().add( comment );
        return comment;
    }

    private Comment create( String commentContent, User user )
    {
        UnitOfWork uow = uowf.currentUnitOfWork();

        EntityBuilder<Comment> builder = uow.newEntityBuilder( Comment.class );
        CommentState state = builder.stateFor( CommentState.class );
        state.comment().set( commentContent );
        Date createdDate = new Date();
        state.createdDate().set( createdDate );
        state.lastUpdatedDate().set( createdDate );
        state.createdBy().set( user );

        return builder.newInstance();
    }
}
