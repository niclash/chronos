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
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.injection.scope.This;

@Mixins( CommentEntity.CommentMixin.class )
interface CommentEntity extends Comment, EntityComposite
{
    public class CommentMixin
        implements Comment
    {
        @This private CommentState state;

        public String content()
        {
            return state.comment().get();
        }

        public void updateContent( String newContent )
        {
            state.lastUpdatedDate().set( new Date() );
            state.comment().set( newContent );
        }

        public Date createdDate()
        {
            return state.createdDate().get();
        }

        public Date lastUpdatedDate()
        {
            return state.lastUpdatedDate().get();
        }

        public User createdBy()
        {
            return state.createdBy().get();
        }
    }
}
