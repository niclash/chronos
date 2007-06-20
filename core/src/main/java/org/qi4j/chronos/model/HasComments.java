package org.qi4j.chronos.model;

import java.util.List;

public interface HasComments
{
    void addComment(Comment comment);

    void removeComment(Comment comment);

    List<Comment> getComments();
}
