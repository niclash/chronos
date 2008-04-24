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

import java.util.List;
import java.util.ArrayList;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.service.CommentService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.entity.Identity;

public abstract class CommentDataProvider extends AbstractSortableDataProvider<Comment, String>
{
    public int getSize()
    {
        // TODO kamil: migrate
//        return getCommentService().countAll( getHasComments() );
        return getHasComments().comments().size();
    }

    public String getId( Comment comment )
    {
        return ( (Identity) comment).identity().get();
    }

    private CommentService getCommentService()
    {
        return ChronosWebApp.getServices().getCommentService();
    }

    public Comment load( String commentId )
    {
//        return getCommentService().get( getHasComments(), commentId.getCreatedDate(), commentId.getUserId() );
        for( Comment comment : getHasComments().comments() )
        {
            if( commentId.equals( ( (Identity) comment ).identity().get() ) )
            {
                return comment;
            }
        }

        return null;
    }

    public List<Comment> dataList( int first, int count )
    {
        // TODO kamil: migrate
//        return getCommentService().findAll( getHasComments(), new FindFilter( first, count ) );
        return new ArrayList<Comment>( getHasComments().comments() );
    }

    public abstract HasComments getHasComments();
}
