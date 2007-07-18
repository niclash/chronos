/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.ui.project.mixins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.qi4j.api.CompositeFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.chronos.ui.project.composites.ProjectEditPageComposite;
import org.qi4j.ui.RequestHandler;

public final class ProjectEditPageRequestHandlerMixin implements RequestHandler
{
    @Dependency private CompositeFactory factory;

    public void request( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse )
    {
        ProjectEditPageComposite projectEditPage = factory.newInstance( ProjectEditPageComposite.class );
        projectEditPage.render( httpServletRequest, httpServletResponse );
    }

    public boolean canHandle( HttpServletRequest httpServletRequest )
    {
        // TODO: Implement this
        return true;
    }
}
