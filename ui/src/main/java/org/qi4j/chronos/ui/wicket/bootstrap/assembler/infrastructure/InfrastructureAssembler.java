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
package org.qi4j.chronos.ui.wicket.bootstrap.assembler.infrastructure;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.assembler.LayerAssembler;

public final class InfrastructureAssembler
    implements LayerAssembler
{
    public static final String LAYER_INFRASTRUCTURE = "Infrastructure";
    public static final String MODULE_PERSISTENCE = "persistence";

    public final LayerAssembly createLayerAssembly( ApplicationAssembly anApplicationAssembly )
        throws AssemblyException
    {
        LayerAssembly infrastructure = anApplicationAssembly.newLayerAssembly( LAYER_INFRASTRUCTURE );

        ModuleAssembly persistence = infrastructure.newModuleAssembly( MODULE_PERSISTENCE );
        persistence.addAssembler( new PersistenceModuleAssembler() );

        return infrastructure;
    }
}
