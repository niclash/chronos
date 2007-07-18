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
package org.qi4j.ui.component.modifiers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.ui.RenderFailedException;
import org.qi4j.ui.ServletLifecycle;
import org.qi4j.ui.SubmitFailedException;

public final class HtmlHeaderLifecycleModifier implements ServletLifecycle
{
    @Modifies private ServletLifecycle next;

    public void render( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws RenderFailedException
    {
        PrintWriter printWriter = null;
        try
        {
            httpServletResponse.setContentType( "text/html" );
            httpServletResponse.setHeader( "pragma", "no-cache" );
            printWriter = httpServletResponse.getWriter();
            printWriter.print( "<HTML><HEAD><TITLE>SessionAuthServlet</TITLE></HEAD><BODY>" );
            next.render( httpServletRequest, httpServletResponse );
        }
        catch( IOException e )
        {
            throw new RenderFailedException( "Fail to render HtmlHeader.", e );
        }
    }

    public void submit( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws SubmitFailedException
    {
        // Do nothing
    }
}
