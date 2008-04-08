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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.service.CommentService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.util.DateUtil;

public abstract class CommentTable extends ActionTable<Comment, CommentId>
{
    private CommentDataProvider dataProvider;

    public CommentTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<Comment>( "Delete" )
        {
            public void performAction( List<Comment> comments )
            {
                getCommentService().deleteComments( getHasComments(), comments );
            }
        } );
    }

    public AbstractSortableDataProvider<Comment, CommentId> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new CommentDataProvider()
            {
                public HasComments getHasComments()
                {
                    return CommentTable.this.getHasComments();
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, Comment obj )
    {
        User user = obj.user().get();
        String userId = user.identity().get();

        Date createdDate = obj.createdDate().get();
        item.add( createDetailLink( "user", user.name().get(), userId, createdDate ) );
        item.add( createDetailLink( "createdDate", DateUtil.formatDateTime( createdDate ), userId, obj.createdDate().get() ) );
        //TODO bp.  truncate comment
        item.add( createDetailLink( "comment", obj.text().get(), userId, createdDate ) );
    }

    private SimpleLink createDetailLink( String id, String text, final String userId, final Date createdDate )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
                CommentDetailPage detailPage = new CommentDetailPage( this.getPage() )
                {
                    public Comment getComment()
                    {
                        return getCommentService().get( getHasComments(), createdDate, userId );
                    }
                };

                setResponsePage( detailPage );
            }
        };
    }

    private CommentService getCommentService()
    {
        return ChronosWebApp.getServices().getCommentService();
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "User", "Created Date", "Comment" );
    }

    public abstract HasComments getHasComments();
}
