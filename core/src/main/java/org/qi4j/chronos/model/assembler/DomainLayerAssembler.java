/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.model.assembler;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.assembler.LayerAssembler;
import org.qi4j.library.constraints.annotation.NotNull;

public class DomainLayerAssembler
    implements LayerAssembler
{
    public static final String LAYER_DOMAIN = "domain";

    private static final String MODULE_ENTITIES = "entities";

    public LayerAssembly createLayerAssembly( ApplicationAssembly anApplicationAssembly )
        throws AssemblyException
    {
        LayerAssembly domainLayer = anApplicationAssembly.newLayerAssembly( LAYER_DOMAIN );

        ModuleAssembly entities = domainLayer.newModuleAssembly( MODULE_ENTITIES );
        entities.addAssembler( new EntitiesModuleAssembler() );

        return domainLayer;
    }
}
