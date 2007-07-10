package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.associations.HasComments;

public class HasCommentsMixin implements HasComments
{
    private List<Comment> list;

    public HasCommentsMixin()
    {
        list = new ArrayList<Comment>();
    }

    public void addComment( Comment comment )
    {
        list.add( comment );
    }

    public void removeComment( Comment comment )
    {
        list.remove( comment );
    }

    public Iterator<Comment> commentIterator()
    {
        return list.iterator();
    }
}
