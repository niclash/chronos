package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import org.qi4j.chronos.model.composites.CommentComposite;

public interface HasComments
{
    void addComment(CommentComposite comment);

    void removeComment( CommentComposite comment);

    Iterator<CommentComposite> commentIterator();
}
