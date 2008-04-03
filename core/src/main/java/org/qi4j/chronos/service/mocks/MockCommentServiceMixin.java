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
package org.qi4j.chronos.service.mocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.service.CommentService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.entity.association.ManyAssociation;

public class MockCommentServiceMixin implements CommentService
{
    public List<CommentComposite> findAll( HasComments hasComments, FindFilter findFilter )
    {
        return findAll( hasComments ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<CommentComposite> findAll( HasComments hasComments )
    {
        Iterator<CommentComposite> iter = hasComments.comments().iterator();

        List<CommentComposite> returnList = new ArrayList<CommentComposite>();

        while( iter.hasNext() )
        {
            returnList.add( iter.next() );
        }

        return returnList;
    }

    public int countAll( HasComments hasComments )
    {
        return findAll( hasComments ).size();
    }

    public void update( HasComments hasComments, CommentComposite oldComment, CommentComposite newComment )
    {
        CommentComposite toBeDeleted = null;
        ManyAssociation<CommentComposite> commentsAssociation = hasComments.comments();

        for( CommentComposite comment : commentsAssociation )
        {

            if( comment.text().get().equals( oldComment.text().get() ) &&
                comment.user().get().login().get().name().get().equals( oldComment.user().get().login().get().name().get() ) )
            {
                toBeDeleted = comment;
            }
        }

        commentsAssociation.remove( toBeDeleted );
        commentsAssociation.add( newComment );
    }

    public CommentComposite get( HasComments hasComments, Date createdDate, String userId )
    {

        for( CommentComposite commentComposite : hasComments.comments() )
        {

            String tempId = commentComposite.user().get().identity().get();
            if( commentComposite.createdDate().get().equals( createdDate ) && tempId.equals( userId ) )
            {
                return commentComposite;
            }
        }

        return null;
    }

    public void deleteComments( HasComments hasComments, Collection<CommentComposite> comments )
    {
        for( CommentComposite comment : comments )
        {
            CommentComposite toBeDeleted = null;

            for( CommentComposite commentComposite : hasComments.comments() )
            {

                Login commentsUserLogin = commentComposite.user().get().login().get();
                if( commentComposite.createdDate().get().equals( comment.createdDate().get() ) &&
                    commentsUserLogin.name().get().equals( comment.user().get().login().get().name().get() ) )
                {
                    toBeDeleted = commentComposite;
                    break;
                }
            }

            if( toBeDeleted != null )
            {
                hasComments.comments().remove( toBeDeleted );
            }
        }
    }
}
