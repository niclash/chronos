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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentEntity;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommentTable extends ActionTable<IModel, String>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( CommentTable.class );
    private AbstractSortableDataProvider<IModel, String> dataProvider;
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_FAIL = "deleteFailed";

    public CommentTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new DeleteAction<IModel>( getString( DELETE_ACTION ) )
            {
                public void performAction( List<IModel> comments )
                {
                    handleDeleteAction( comments );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
    }

    private void handleDeleteAction( List<IModel> comments )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            final HasComments hasComments = unitOfWork.dereference( getHasComments() );
            for( IModel iModel : comments )
            {
                final Comment comment = (Comment) iModel.getObject();
                hasComments.comments().remove( comment );
                unitOfWork.remove( comment );
            }

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    public AbstractSortableDataProvider<IModel, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new AbstractSortableDataProvider<IModel, String>()
            {
                public int getSize()
                {
                    HasComments hasComments = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( CommentTable.this.getHasComments() );
                    return hasComments.comments().size();
                }

                public String getId( IModel t )
                {
                    return ( (Identity) t.getObject() ).identity().get();
                }

                public IModel load( final String s )
                {
                    return new CompoundPropertyModel(
                        new LoadableDetachableModel()
                        {
                            protected Object load()
                            {
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, CommentEntity.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> models = new ArrayList<IModel>();
                    for( final String commentId : CommentTable.this.dataList( first, count ) )
                    {
                        models.add(
                            new CompoundPropertyModel(
                                new LoadableDetachableModel()
                                {
                                    protected Object load()
                                    {
                                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( commentId, CommentEntity.class );
                                    }
                                }
                            )
                        );

                    }
                    return models;
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, IModel iModel )
    {
        final Comment comment = (Comment) iModel.getObject();
        final User user = comment.user().get();
        final String commentId = ( (Identity) comment ).identity().get();

        Date createdDate = comment.createdDate().get();
        item.add( createDetailLink( "user", user.fullName().get(), commentId ) );
        item.add( createDetailLink( "createdDate", DateUtil.formatDateTime( createdDate ), commentId ) );
        item.add( createDetailLink( "comment", comment.text().get(), commentId ) );
    }

    private SimpleLink createDetailLink( String id, String text, final String commentId )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
                //TODO
//                CommentDetailPage detailPage = new CommentDetailPage( CommentTable.this.getPage(), commentId );
//                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "User", "Created Date", "Comment" );
    }

    public abstract List<String> dataList( int first, int count );

    public abstract HasComments getHasComments();
}
