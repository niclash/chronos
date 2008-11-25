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
package org.qi4j.chronos.domain.model.account;

import org.qi4j.chronos.domain.model.common.name.MutableName;
import org.qi4j.chronos.domain.model.common.name.ReferenceName;
import org.qi4j.chronos.domain.model.common.name.MutableReferenceName;
import org.qi4j.chronos.domain.model.location.address.Address;
import org.qi4j.library.constraints.annotation.MaxLength;
import org.qi4j.composite.Optional;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public interface AccountDetail extends MutableName, MutableReferenceName
{
    @Optional Address address();

    void changeAddress( @Optional Address address );
}
