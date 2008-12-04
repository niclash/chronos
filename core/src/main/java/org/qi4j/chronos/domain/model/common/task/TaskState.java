/*  Copyright 2008 Edward Yakop.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
* implied.
*
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.qi4j.chronos.domain.model.common.task;

import java.util.Date;
import org.qi4j.chronos.domain.model.WorkEntry;
import org.qi4j.chronos.domain.model.common.comment.Comment;
import org.qi4j.chronos.domain.model.common.description.DescriptionState;
import org.qi4j.chronos.domain.model.user.User;
import org.qi4j.entity.association.Association;
import org.qi4j.entity.association.SetAssociation;
import org.qi4j.property.Property;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public interface TaskState extends DescriptionState
{
    Property<String> title();

    Property<TaskStatus> status();

    Property<TaskPriority> priority();

    Property<Date> createdDate();

    Association<User> createdBy();

    SetAssociation<Comment> comments();

    SetAssociation<WorkEntry> workEntries();

    Association<User> assignedTo();
}
