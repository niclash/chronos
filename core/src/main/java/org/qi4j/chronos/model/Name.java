/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 11, 2008
 * Time: 9:25:53 PM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.model;

import org.qi4j.property.Property;
import org.qi4j.composite.Optional;

public interface Name
{
    @Optional Property<String> name();
}
