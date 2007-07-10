package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.Comment;

public class CommentMixin implements Comment
{
    private String comment;

    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }
}
