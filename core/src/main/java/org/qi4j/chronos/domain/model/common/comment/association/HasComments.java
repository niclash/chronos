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
package org.qi4j.chronos.domain.model.common.comment.association;

import org.qi4j.chronos.domain.model.common.comment.Comment;
import org.qi4j.chronos.domain.model.common.comment.assembly.HasCommentsMixin;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( HasCommentsMixin.class )
public interface HasComments
{
    Query<Comment> comments();

    Comment addComment( String commentContent, User user );
}