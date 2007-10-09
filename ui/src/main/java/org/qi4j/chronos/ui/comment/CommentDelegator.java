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
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.model.composites.CommentComposite;

//TODO bp. we don't need this when comment can be made serilizable
public class CommentDelegator implements Serializable
{
    private Date createdDate;
    private String userId;

    public CommentDelegator( CommentComposite comment )
    {
        createdDate = comment.getCreatedDate();
        userId = ( (Identity) comment.getUser() ).getIdentity();
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

        CommentDelegator that = (CommentDelegator) o;

        if( createdDate != null ? !createdDate.equals( that.createdDate ) : that.createdDate != null )
        {
            return false;
        }
        if( userId != null ? !userId.equals( that.userId ) : that.userId != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        int result;
        result = ( createdDate != null ? createdDate.hashCode() : 0 );
        result = 31 * result + ( userId != null ? userId.hashCode() : 0 );
        return result;
    }
}
