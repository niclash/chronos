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
package org.qi4j.chronos.domain.model.common.assembly;

import java.util.Date;
import org.qi4j.chronos.domain.model.common.comment.Comment;
import org.qi4j.chronos.domain.model.common.comment.CommentFactory;
import org.qi4j.chronos.domain.model.common.comment.CommentState;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( CommentFactoryService.CommentFactoryMixin.class )
interface CommentFactoryService extends CommentFactory, ServiceComposite
{
    class CommentFactoryMixin
        implements CommentFactory
    {
        @Structure private UnitOfWorkFactory uowf;

        public final Comment create( String commentContent, User user )
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
}
