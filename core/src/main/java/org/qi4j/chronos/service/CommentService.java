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
package org.qi4j.chronos.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentComposite;

public interface CommentService
{
    List<CommentComposite> findAll( HasComments hasComments, FindFilter findFilter );

    List<CommentComposite> findAll( HasComments hasComments );

    int countAll( HasComments hasComments );

    CommentComposite get( HasComments hasComments, Date createdDate );

    void update( HasComments hasComments, CommentComposite oldComment, CommentComposite newCommnent );

    CommentComposite get( HasComments hasComments, Date createdDate, String userId );

    void deleteComments( HasComments hasComments, Collection<CommentComposite> comments );
}
