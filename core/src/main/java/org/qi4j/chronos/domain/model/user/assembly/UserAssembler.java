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
package org.qi4j.chronos.domain.model.user.assembly;

import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import static org.qi4j.structure.Visibility.application;
import static org.qi4j.structure.Visibility.layer;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public final class UserAssembler
    implements Assembler
{
    public final void assemble( ModuleAssembly aModule )
        throws AssemblyException
    {
        aModule.addEntities(
            SystemRoleEntity.class,
            AdminEntity.class,
            ContactPersonEntity.class
        ).visibleIn( layer );

        aModule.addServices(
            SystemRoleBootstrap.class,
            AdminBootstrap.class
        ).instantiateOnStartup();

        aModule.addComposites(
            UserDetailComposite.class,
            LoginComposite.class
        );

        aModule.addServices(
            SystemRoleRepositoryService.class,
            AdminRepositoryService.class
        ).visibleIn( application );
    }
}
