/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
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
package org.qi4j.ui.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.qi4j.api.Composite;
import org.qi4j.api.CompositeFactory;
import org.qi4j.api.CompositeInstantiationException;
import org.qi4j.runtime.CompositeFactoryImpl;
import org.qi4j.ui.ServletInitFailedException;
import org.qi4j.ui.WebApplication;
import org.qi4j.ui.request.Request;
import org.qi4j.ui.request.RequestProcessor;
import org.qi4j.ui.response.Response;

public class Servlet extends HttpServlet
{
    private static final String WEB_APPLICATION = "webApplication";

    private CompositeFactory factory;

    private String webApplicationClassName;

    private WebApplication application;

    public void init() throws ServletException
    {
        factory = new CompositeFactoryImpl();

        webApplicationClassName = getInitParameter( WEB_APPLICATION );

        System.out.println( "WebApplicationClassName is " + webApplicationClassName );
        try
        {
            Class webApplicationClass = Class.forName( webApplicationClassName );

            if( WebApplication.class.isAssignableFrom( webApplicationClass ) )
            {
                Composite composite = factory.newInstance( webApplicationClass );
                application = (WebApplication) composite;

                application.init();
            }
            else
            {
                throw new ServletInitFailedException( webApplicationClassName + " not instance of WebApplication." );
            }
        }
        catch( CompositeInstantiationException e )
        {
            throw new ServletInitFailedException( "Fail to instantiate WebApplication composite", e );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletInitFailedException( webApplicationClassName + " doesn't exist.", e );
        }
    }

    protected void doPost( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws ServletException, IOException
    {
        //NOT implemented yet.
    }

    protected void doGet( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws ServletException, IOException
    {
        Request request = application.newRequest( httpServletRequest );
        Response response = application.newResponse( httpServletResponse );

        RequestProcessor requestProcessor = application.newRequestProcessor( request, response );

        requestProcessor.setWebApplication( application );

        requestProcessor.request();
    }

    public void destroy()
    {
    }
}