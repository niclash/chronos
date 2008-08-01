package org.qi4j.chronos.ui.comment;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentEntity;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public final class CommentDataProvider extends AbstractSortableDataProvider<Comment>
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasComments> hasCommentsModel;

    public CommentDataProvider( IModel<? extends HasComments> hasCommentsModel )
    {
        this.hasCommentsModel = hasCommentsModel;
    }

    public IModel<Comment> load( String id )
    {
        return new ChronosDetachableModel<Comment>( ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().getReference( id, CommentEntity.class ) );
    }

    public int size()
    {
        return hasCommentsModel.getObject().comments().size();
    }

    public List<Comment> dataList( int first, int count )
    {
        //TODO
        List<Comment> comments = new ArrayList<Comment>( hasCommentsModel.getObject().comments() );

        return comments.subList( first, first + count );
    }

}
