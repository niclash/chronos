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
package org.qi4j.chronos.domain.model.project;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.domain.model.project.assembly.ProjectAssembler;
import org.qi4j.chronos.domain.model.common.comment.assembly.CommentAssembler;
import org.qi4j.index.rdf.assembly.RdfMemoryStoreAssembler;
import org.qi4j.entitystore.memory.MemoryEntityStoreService;
import org.qi4j.test.AbstractQi4jTest;
import org.qi4j.spi.uuid.UuidIdentityGeneratorService;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public abstract class AbstractProjectTest extends AbstractQi4jTest
{
    public final void assemble( ModuleAssembly module )
        throws AssemblyException
    {
        new RdfMemoryStoreAssembler().assemble( module );
        module.addServices( MemoryEntityStoreService.class,
                            UuidIdentityGeneratorService.class );
        new ProjectAssembler().assemble( module );
        new CommentAssembler().assemble( module );
    }
}
