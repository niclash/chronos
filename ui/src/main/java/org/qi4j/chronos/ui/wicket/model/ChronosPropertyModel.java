/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.wicket.model;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.util.lang.PropertyResolverConverter;
import org.apache.wicket.util.string.Strings;
import org.qi4j.entity.Entity;

/**
 * @author Lan Boon Ping
 */
public class ChronosPropertyModel<T> implements
                                     IChainingModel<T>
{
    private static final long serialVersionUID = 1L;

    private Object target;

    private String propertyExpression;

    public ChronosPropertyModel( final Object modelObject, String propertyExpression )
    {
        if( modelObject == null )
        {
            throw new IllegalArgumentException( "Parameter modelObject cannot be null" );
        }

        if( modelObject instanceof Entity )
        {
            target = new ChronosDetachableModel( modelObject );
        }
        else
        {
            target = modelObject;
        }

        this.propertyExpression = propertyExpression;
    }

    public void detach()
    {
        // Detach nested object if it's a detachable
        if( target instanceof IDetachable )
        {
            ( (IDetachable) target ).detach();
        }
    }

    public IModel<?> getChainedModel()
    {
        if( target instanceof IModel )
        {
            return (IModel<?>) target;
        }
        return null;
    }

    @SuppressWarnings( "unchecked" )
    public T getObject()
    {
        if( Strings.isEmpty( propertyExpression ) )
        {
            // Return a meaningful value for an empty property expression
            return (T) getTarget();
        }

        final Object target = getTarget();

        if( target != null )
        {
            if( target instanceof Entity )
            {
                Object returnedValue = ChronosPropertyResolver.getValue( propertyExpression, target );

                if( returnedValue != null && returnedValue instanceof Entity )
                {
                    //always wrap entiry with detachableModel to ensure that it can be accessed with current Unit of Work
                    return (T) new ChronosDetachableModel( returnedValue );
                }
                else
                {
                    return (T) returnedValue;
                }
            }
            else
            {
                return (T) PropertyResolver.getValue( propertyExpression, target );
            }
        }
        return null;
    }

    public final String getPropertyExpression()
    {
        return propertyExpression;
    }

    public void setChainedModel( IModel<?> model )
    {
        target = model;
    }

    @SuppressWarnings( "unchecked" )
    public void setObject( Object object )
    {
        if( Strings.isEmpty( propertyExpression ) )
        {
            if( target instanceof IModel )
            {
                ( (IModel) target ).setObject( object );
            }
            else
            {
                target = object;
            }
        }
        else
        {
            if( getTarget() instanceof Entity )
            {
                ChronosPropertyResolver.setValue( propertyExpression, getTarget(), object );
            }
            else
            {
                PropertyResolverConverter prc = new PropertyResolverConverter( Application.get().getConverterLocator(),
                                                                               Session.get().getLocale() );
                PropertyResolver.setValue( propertyExpression, getTarget(), object, prc );
            }
        }
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer( "Model:classname=[" );
        sb.append( getClass().getName() ).append( "]" );
        sb.append( ":nestedModel=[" ).append( target ).append( "]" );
        return sb.toString();
    }

    public final Object getTarget()
    {
        Object object = target;
        while( object instanceof IModel )
        {
            Object tmp = ( (IModel) object ).getObject();
            if( tmp == object )
            {
                break;
            }
            object = tmp;
        }
        return object;
    }
}
