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
package org.qi4j.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.qi4j.api.Composite;
import org.qi4j.api.CompositeFactory;
import org.qi4j.api.CompositeInstantiationException;
import org.qi4j.runtime.CompositeFactoryImpl;

/**
 * Upon request, parameters will be parsed and used to determine the page and action to invoke.
 * <p/>
 * Following is example of URL:
 * <a>http://localhost:8080/chronos/?page=org.qi4j.chronos.ui.component.ProjectEditPage&action=render</a>
 * <p/>
 * This servlet would load page as specified in page parameter, i.e.
 * {@link org.qi4j.chronos.ui.component.ProjectEditPage} and
 * invoke the specified action, i.e.
 * {@link org.qi4j.chronos.ui.component.ProjectEditPage#render(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)}.
 */
public final class Servlet extends HttpServlet
{
    private CompositeFactory factory;

    public void init() throws ServletException
    {
        super.init();
        factory = new CompositeFactoryImpl();
    }


    protected void service( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws ServletException, IOException
    {
        String pageClasspath = httpServletRequest.getParameter( "page" );
        String pageAction = httpServletRequest.getParameter( "action" );

        if( pageClasspath == null || pageAction == null )
        {
            displayErrorPage( httpServletResponse, "Please provide page and action." );
        }


        try
        {
            Composite composite = getComposite( pageClasspath, httpServletRequest, httpServletResponse );

            Class pageClass = Class.forName( pageClasspath );
            Method method = pageClass.getMethod( pageAction, HttpServletRequest.class, HttpServletResponse.class );

            method.invoke( composite, httpServletRequest, httpServletResponse );
        }
        catch( ClassNotFoundException e )
        {
            displayErrorPage( httpServletResponse, "Page for [" + pageClasspath + "] does not exist." );
        }
        catch( NoSuchMethodException e )
        {
            displayErrorPage( httpServletResponse, "Method render(httpServletRequest, httpServletResponse) does not " +
                                                   "exist in page [" + pageClasspath + "]" );
        }
        catch( IllegalAccessException e )
        {
            displayErrorPage( httpServletResponse, "Failed to invoke render(httpServletRequest, httpServletResponse) " +
                                                   "method in page [" + pageClasspath + "]" );
        }
        catch( InvocationTargetException e )
        {
            displayErrorPage( httpServletResponse, "Failed to invoke render(httpServletRequest, httpServletResponse) " +
                                                   "method in page [" + pageClasspath + "]" );
        }
    }

    private Composite getComposite( String pageClasspath, HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse ) throws ClassNotFoundException, IOException
    {
        HttpSession session = httpServletRequest.getSession();
        Composite composite = (Composite) session.getAttribute( pageClasspath );
        Class pageClass = Class.forName( pageClasspath );

        if( composite == null )
        {
            if( ServletLifecycle.class.isAssignableFrom( pageClass ) )
            {
                try
                {
                    composite = factory.newInstance( pageClass );
                }
                catch( CompositeInstantiationException e )
                {
                    displayErrorPage( httpServletResponse, "Instantion Exception occur for page [" + pageClasspath + "]" );
                }
            }
            else
            {
                displayErrorPage( httpServletResponse, "Page must implements " + ServletLifecycle.class.getName() );
            }

            session.setAttribute( pageClasspath, composite );
        }
        return composite;
    }

    private void displayErrorPage( HttpServletResponse httpServletResponse, String errorMessage )
        throws IOException
    {
        httpServletResponse.setContentType( "text/html" );
        httpServletResponse.setHeader( "pragma", "no-cache" );
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.print( "<HTML><HEAD><TITLE>SessionAuthServlet</TITLE></HEAD><BODY>" );
        printWriter.print( "<FORM METHOD=POST>" + errorMessage + "</FORM></BODY></HTML>" );
        printWriter.close();
    }
}