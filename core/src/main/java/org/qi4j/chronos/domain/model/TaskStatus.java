/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 11, 2008
 * Time: 9:22:29 PM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.domain.model;

import org.qi4j.property.Property;

public interface TaskStatus
{
    Property<TaskStatusEnum> taskStatus();
}
