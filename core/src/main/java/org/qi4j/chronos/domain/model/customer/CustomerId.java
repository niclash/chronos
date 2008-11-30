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
package org.qi4j.chronos.domain.model.customer;

import java.io.Serializable;
import org.qi4j.chronos.domain.model.ValueObject;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public final class CustomerId
    implements ValueObject<CustomerId>, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String idString;

    public CustomerId( String customerIdString )
    {
        idString = customerIdString;
    }

    public final String idString()
    {
        return idString;
    }

    public final boolean sameValueAs( CustomerId other )
    {
        return other != null && idString.equals( other.idString );
    }
}
