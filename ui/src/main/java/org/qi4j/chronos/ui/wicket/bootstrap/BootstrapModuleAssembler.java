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
package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.markup.html.pages.InternalErrorPage;
import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.ui.wicket.authentication.LoginPage;
import org.qi4j.chronos.ui.wicket.bootstrap.serialization.Qi4jObjectStreamFactory;
import static org.qi4j.api.common.Visibility.layer;

/**
 * @author edward.yakop@gmail.com
 */
public class BootstrapModuleAssembler
    implements Assembler
{
    public void assemble( ModuleAssembly aModule )
        throws AssemblyException
    {
        aModule.addObjects(
            InternalErrorPage.class
        ).visibleIn( layer );

        aModule.addObjects(
            ChronosWebApp.class,
            ChronosPageFactory.class,
            ChronosSession.class,
            LoginPage.class,
            Qi4jObjectStreamFactory.class
        );

        // TODO: Remove this
        aModule.addObjects(
            DummyDataInitializer.class
        );
    }
}
