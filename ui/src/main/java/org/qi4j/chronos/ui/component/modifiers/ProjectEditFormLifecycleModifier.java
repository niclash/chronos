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

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.qi4j.api.CompositeFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.ui.association.HasProject;
import org.qi4j.ui.RenderFailedException;
import org.qi4j.ui.ServletLifecycle;
import org.qi4j.ui.SubmitFailedException;
import org.qi4j.ui.association.HasComponents;
import org.qi4j.ui.component.Label;
import org.qi4j.ui.component.SubmitButton;
import org.qi4j.ui.component.TextField;

public final class ProjectEditFormLifecycleModifier implements ServletLifecycle
{
    @Uses private HasProject hasProject;
    @Uses private HasComponents hasComponents;
    @Modifies private ServletLifecycle next;
    @Dependency private CompositeFactory factory;

    public void render( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws RenderFailedException
    {
        try
        {
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.print( "<FORM>" );

            Label projectNameLabel = factory.newInstance( Label.class );
            projectNameLabel.setValue( "Project Name: " );
            projectNameLabel.render( httpServletRequest, httpServletResponse );

            TextField projectNameInput = factory.newInstance( TextField.class );
            projectNameInput.setIdentity( "projectName" );

            Project project = hasProject.getProject();
            String projectName = project.getName();
            projectNameInput.setValue( projectName );

            hasComponents.addComponent( projectNameInput );
            projectNameInput.render( httpServletRequest, httpServletResponse );

            SubmitButton submitBtn = factory.newInstance( SubmitButton.class );
            submitBtn.setValue( "Save" );
            submitBtn.render( httpServletRequest, httpServletResponse );

            printWriter.print( "</FORM>" );
        }
        catch( IOException e )
        {
            throw new RenderFailedException( "Fail to render Project edit form.", e );
        }
    }

    public void submit( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws SubmitFailedException
    {
        TextField projectNameInput = (TextField) hasComponents.getComponent( "projectName" );
        String projectName = projectNameInput.getValue();

        Project project = hasProject.getProject();
        project.setName( projectName );

        // TODO: Persist project here
    }
}