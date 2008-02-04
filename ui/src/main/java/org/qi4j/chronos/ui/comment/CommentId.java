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

import java.io.Serializable;
import java.util.Date;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.entity.Identity;

public class CommentId implements Serializable
{
    private String userId;
    private Date createdDate;

    public CommentId( CommentComposite comment )
    {
        this.userId = comment.user().get().identity().get();
        createdDate = comment.createdDate().get();
    }

    public String getUserId()
    {
        return userId;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public boolean equals( Object o )
    {
        if( this == o )
        {
            return true;
        }
        if( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        CommentId commentId = (CommentId) o;

        if( createdDate != null ? !createdDate.equals( commentId.createdDate ) : commentId.createdDate != null )
        {
            return false;
        }
        if( userId != null ? !userId.equals( commentId.userId ) : commentId.userId != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        int result;
        result = ( userId != null ? userId.hashCode() : 0 );
        result = 31 * result + ( createdDate != null ? createdDate.hashCode() : 0 );
        return result;
    }
}
