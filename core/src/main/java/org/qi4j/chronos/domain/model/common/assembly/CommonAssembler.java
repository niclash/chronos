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
package org.qi4j.chronos.domain.model.common.assembly;

import static org.qi4j.api.common.Visibility.*;
import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.domain.model.common.comment.assembly.CommentAssembler;

public class CommonAssembler
    implements Assembler
{
    public void assemble( ModuleAssembly aModule )
        throws AssemblyException
    {
        new CommentAssembler().assemble( aModule );

        aModule.addEntities(
            LegalConditionEntity.class,
            MoneyEntity.class,
            PeriodEntity.class,
            PriceRateEntity.class,
            PriceRateScheduleEntity.class
        ).visibleIn( layer );

        aModule.addServices(
            LegalConditionFactoryService.class,
            MoneyFactoryService.class,
            PeriodFactoryService.class
        ).visibleIn( application );
    }
}
