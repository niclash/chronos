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

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.ui.InitFailedException;
import org.qi4j.ui.RenderFailedException;
import org.qi4j.ui.response.Response;
import org.qi4j.ui.association.HasComponents;
import org.qi4j.ui.component.Component;
import org.qi4j.ui.component.ComponentLifecycle;
import org.qi4j.ui.component.UIField;
import org.qi4j.ui.model.Model;
import org.qi4j.ui.model.ModelComposite;

public final class ContainerSetupModifier
    implements ComponentLifecycle
{
    @Dependency private CompositeBuilderFactory factory;
    @Modifies private ComponentLifecycle next;
    @Uses private HasComponents hasComponents;

    //TODO bp. create a tagging interface for it?
    @Uses private Object thisObject;

    public void init() throws InitFailedException
    {
        findUIField( thisObject.getClass() );
    }

    private void findUIField( Class aClass )
    {
        Method[] methods = aClass.getMethods();

        for( Method method : methods )
        {
            UIField uiField = method.getAnnotation( UIField.class );

            if( uiField != null && method.getParameterTypes().length == 0 &&
                method.getReturnType() != Void.TYPE )
            {
                try
                {
                    System.out.println( "Before invoking method: " + method );
                    Object obj = method.invoke( thisObject );
                    System.out.println( "method: " + method );
                    Component component = resolveUIField( uiField, obj );
                    System.out.println( "Componen: " + component );
                    hasComponents.addComponent( component );
                }
                catch( Exception e )
                {
                    throw new InitFailedException( e.getMessage(), e );
                }
            }
        }

        iterateClasses( aClass.getInterfaces() );
    }

    private void iterateClasses( Class[] classes )
    {
        for( Class aClass : classes )
        {
            findUIField( aClass );
        }
    }

    private Component resolveUIField( UIField uiField, Object obj )
    {
        Class<? extends Component> componentClass = uiField.type();
        Component component = factory.newCompositeBuilder( componentClass ).newInstance();

        Model model = factory.newCompositeBuilder( ModelComposite.class ).newInstance();
        model.setObject( obj );

        component.setModel( model );

        return component;
    }

    public void dispose()
    {
        next.dispose();
    }

    public void render( Response response )
        throws RenderFailedException
    {
        next.render( response );
    }

    public void render( HttpServletRequest request, HttpServletResponse response )
        throws RenderFailedException
    {
//        next.render( request, response );
    }
}
