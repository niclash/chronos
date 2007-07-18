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
package org.qi4j.ui.component.modifiers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.qi4j.api.CompositeFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.ui.RenderFailedException;
import org.qi4j.ui.component.Component;
import org.qi4j.ui.component.ComponentLifecycle;
import org.qi4j.ui.component.UIField;
import org.qi4j.ui.component.UIFieldResolver;
import org.qi4j.ui.model.association.HasModel;

public final class EditPanelModifier implements ComponentLifecycle
{
    @Modifies private ComponentLifecycle next;

    //TODO bp. create a tagging interface for it?
    @Uses private Object thisObject;

    @Dependency private CompositeFactory factory;

    @Uses private HasModel hasModel;

    private List<Component> componentList;

    private void findUIField( Method[] methods )
    {
        for( Method method : methods )
        {
            UIField uiField = method.getAnnotation( UIField.class );

            if( uiField != null && method.getParameterTypes().length == 0 &&
                method.getReturnType() != Void.TYPE )
            {
                try
                {
                    Object obj = method.invoke( thisObject, null );

                    Component component = UIFieldResolver.resolveUIField( factory, uiField, obj );

                    componentList.add( component );
                }
                catch( Exception err )
                {
                    err.printStackTrace();
                    throw new RuntimeException( err.getMessage(), err );
                }
            }
        }
    }

    public void init()
    {
        componentList = new ArrayList<Component>();

        Method[] methods = thisObject.getClass().getMethods();

        findUIField( methods );

        Class[] interfaces = thisObject.getClass().getInterfaces();

        for( Class intface : interfaces )
        {
            methods = intface.getMethods();

            findUIField( methods );
        }
    }

    public void dispose()
    {
        next.dispose();
    }

    public void render( HttpServletRequest request, HttpServletResponse response ) throws RenderFailedException
    {
        try
        {
            PrintWriter printWriter = response.getWriter();
            printWriter.write( "<table>" );

            //render components
            for( Component component : componentList )
            {
                printWriter.write( "<tr><td>" );
                component.render( request, response );
                printWriter.write( "</td></tr>" );
            }

            printWriter.write( "</table>" );
            next.render( request, response );
        }
        catch( IOException e )
        {
            throw new RenderFailedException( "Fail to render edit panel.", e );
        }
    }
}