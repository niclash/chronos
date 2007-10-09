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
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.service.CommentService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class CommentDataProvider extends AbstractSortableDataProvider<CommentComposite, CommentId>
{
    public int getSize()
    {
        return getCommentService().countAll( getHasComments() );
    }

    public CommentId getId( CommentComposite t )
    {
        return new CommentId( t );
    }

    private CommentService getCommentService()
    {
        return ChronosWebApp.getServices().getCommentService();
    }

    public CommentComposite load( CommentId commentId )
    {
        return getCommentService().get( getHasComments(), commentId.getCreatedDate(), commentId.getUserId() );
    }

    public List<CommentComposite> dataList( int first, int count )
    {
        return getCommentService().findAll( getHasComments(), new FindFilter( first, count ) );
    }

    public abstract HasComments getHasComments();
}
