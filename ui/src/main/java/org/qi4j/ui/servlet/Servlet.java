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

import org.qi4j.api.CompositeFactory;
import org.qi4j.runtime.CompositeFactoryImpl;
import org.qi4j.ui.WebApplication;
import org.qi4j.ui.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

            application = (WebApplication) factory.newInstance( webApplicationClass );

            application.setCompositeFactory( factory );
        }
        catch( Exception err )
        {
            err.printStackTrace();
            throw new RuntimeException( err );
        }
    }

    protected void doPost( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws ServletException, IOException
    {
        //NOT implemented yet.
    }

    protected void doGet( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws ServletException, IOException
    {
        RequestHandler requestHandler = application.getRequestHandler( httpServletRequest );
        requestHandler.request( httpServletRequest, httpServletResponse );
    }

    public void destroy()
    {

    }
}