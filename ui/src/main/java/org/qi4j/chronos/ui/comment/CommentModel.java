package org.qi4j.chronos.ui.comment;

import java.util.ArrayList;
import java.util.List;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel;

public class CommentModel extends ChronosDetachableModel<Comment>
{
    private static final long serialVersionUID = 1L;

    public CommentModel( Comment object )
    {
        super( object );
    }

}
