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
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.mixin.Mixins;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( CommentFactory.CommentFactoryMixin.class )
interface CommentFactory extends ServiceComposite
{
    Comment create( String comment, User user );

    abstract class CommentFactoryMixin
        implements CommentFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public final Comment create( String commentContent, User user )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            EntityBuilder<Comment> builder = uow.newEntityBuilder( Comment.class );
            CommentState state = builder.instanceFor( CommentState.class );
            state.comment().set( commentContent );
            Date createdDate = new Date();
            state.createdDate().set( createdDate );
            state.lastUpdatedDate().set( createdDate );
            state.createdBy().set( user );

            return builder.newInstance();
        }
    }
}
