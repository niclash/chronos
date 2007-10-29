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
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.service.CommentService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.persistence.Identity;

public class MockCommentServiceMixin implements CommentService
{
    public List<CommentComposite> findAll( HasComments hasComments, FindFilter findFilter )
    {
        return findAll( hasComments ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<CommentComposite> findAll( HasComments hasComments )
    {
        Iterator<CommentComposite> iter = hasComments.commentIterator();

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

    public CommentComposite get( HasComments hasComments, Date createdDate )
    {
        List<CommentComposite> list = findAll( hasComments );

        for( CommentComposite comment : list )
        {
            if( comment.getCreatedDate().equals( createdDate ) )
            {
                return comment;
            }
        }

        return null;
    }

    public void update( CommentComposite commentComposite )
    {
        //nothing
    }

    public CommentComposite get( HasComments hasComments, Date createdDate, String userId )
    {
        Iterator<CommentComposite> iterator = hasComments.commentIterator();

        while( iterator.hasNext() )
        {
            CommentComposite comment = iterator.next();

            String tempId = ( (Identity) comment.getUser() ).getIdentity();
            if( comment.getCreatedDate().equals( createdDate ) && tempId.equals( userId ) )
            {
                return comment;
            }
        }

        return null;
    }

    public void deleteComments( HasComments hasComments, Collection<CommentComposite> comments )
    {
        for( CommentComposite comment : comments )
        {
            CommentComposite toBeDeleted = null;
            Iterator<CommentComposite> commentIter = hasComments.commentIterator();

            while( commentIter.hasNext() )
            {
                CommentComposite temp = commentIter.next();

                if( temp.getCreatedDate().equals( comment.getCreatedDate() ) && temp.getUser().getLogin().getName().equals( comment.getUser().getLogin().getName() ) )
                {
                    toBeDeleted = temp;
                    break;
                }
            }

            if( toBeDeleted != null )
            {
                hasComments.removeComment( toBeDeleted );
            }
        }
    }
}
