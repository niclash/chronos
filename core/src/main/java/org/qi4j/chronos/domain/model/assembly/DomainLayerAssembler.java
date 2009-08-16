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
package org.qi4j.chronos.domain.model.assembly;

import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.domain.model.account.assembly.AccountAssembler;
import org.qi4j.chronos.domain.model.common.assembly.CommonAssembler;
import org.qi4j.chronos.domain.model.project.assembly.ProjectAssembler;
import org.qi4j.chronos.util.assembler.LayerAssembler;

public class DomainLayerAssembler
    implements LayerAssembler
{
    public static final String LAYER_DOMAIN = "domain";

    private static final String MODULE_ACCOUNT = "account";
    private static final String MODULE_COMMON = "common";
    private static final String MODULE_PROJECT = "project";

    public LayerAssembly createLayerAssembly( ApplicationAssembly anApplicationAssembly )
        throws AssemblyException
    {
        LayerAssembly domainLayer = anApplicationAssembly.layerAssembly( LAYER_DOMAIN );

        ModuleAssembly accountModule = domainLayer.moduleAssembly( MODULE_ACCOUNT );
        new AccountAssembler().assemble( accountModule );

        ModuleAssembly commonModule = domainLayer.moduleAssembly( MODULE_COMMON );
        new CommonAssembler().assemble( commonModule );

        ModuleAssembly projectModule = domainLayer.moduleAssembly( MODULE_PROJECT );
        new ProjectAssembler().assemble( projectModule );

        return domainLayer;
    }
}
