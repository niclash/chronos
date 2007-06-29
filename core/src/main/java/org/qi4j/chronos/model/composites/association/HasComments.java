package org.qi4j.chronos.model.composites.association;

import java.util.List;
import java.util.Iterator;
import java.io.Serializable;
import org.qi4j.chronos.model.composites.CommentComposite;

public interface HasComments extends Serializable
{
    void addComment(CommentComposite comment);

    void removeComment( CommentComposite comment);

    Iterator<CommentComposite> commentIterator();
}
