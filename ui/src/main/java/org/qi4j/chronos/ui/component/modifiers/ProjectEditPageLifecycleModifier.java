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
package org.qi4j.chronos.ui.component.modifiers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.qi4j.api.CompositeFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.association.HasProject;
import org.qi4j.chronos.ui.component.ProjectEditForm;
import org.qi4j.ui.RenderFailedException;
import org.qi4j.ui.ServletLifecycle;
import org.qi4j.ui.SubmitFailedException;
import org.qi4j.ui.association.HasForm;
import org.qi4j.ui.component.HtmlFooter;
import org.qi4j.ui.component.HtmlHeader;

public final class ProjectEditPageLifecycleModifier implements ServletLifecycle
{
    @Modifies private ServletLifecycle next;
    @Dependency private CompositeFactory factory;
    @Uses private HasForm<ProjectEditForm> hasForm;
    @Uses private HasProject hasProject;

    public void render( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws RenderFailedException
    {
        Project project = factory.newInstance( ProjectEntityComposite.class );
        hasProject.setProject( project );

        // Setup page header
        HtmlHeader htmlHeader = factory.newInstance( HtmlHeader.class );
        htmlHeader.render( httpServletRequest, httpServletResponse );

        ProjectEditForm editForm = factory.newInstance( ProjectEditForm.class );
        hasForm.setForm( editForm );
        editForm.setProject( project );
        editForm.render( httpServletRequest, httpServletResponse );

        // Setup page footer
        HtmlFooter htmlFooter = factory.newInstance( HtmlFooter.class );
        htmlFooter.render( httpServletRequest, httpServletResponse );

        next.render( httpServletRequest, httpServletResponse );
    }

    public void submit( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws SubmitFailedException
    {
        ProjectEditForm form = hasForm.getForm();
        form.submit( httpServletRequest, httpServletResponse );
    }
}
