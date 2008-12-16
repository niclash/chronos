package org.qi4j.chronos.ui.wicket.model;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.util.lang.PropertyResolverConverter;
import org.apache.wicket.util.string.Strings;
import org.qi4j.api.entity.Entity;

/**
 * @author Lan Boon Ping
 */
public abstract class AbstractChronosPropertyModel<T> implements
                                                      IChainingModel<T>
{
    private static final long serialVersionUID = 1L;

    private Object target;

    public AbstractChronosPropertyModel( final Object modelObject )
    {
        if( modelObject == null )
        {
            throw new IllegalArgumentException( "Parameter modelObject cannot be null" );
        }

        if( modelObject instanceof Entity )
        {
            target = new ChronosEntityModel( modelObject );
        }
        else
        {
            target = modelObject;
        }
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
        if( Strings.isEmpty( getPropertyExpression() ) )
        {
            // Return a meaningful value for an empty property expression
            return (T) getTarget();
        }

        final Object target = getTarget();

        if( target != null )
        {
            if( target instanceof Entity )
            {
                Object returnedValue = ChronosPropertyResolver.getValue( getPropertyExpression(), target );

                if( returnedValue != null && returnedValue instanceof Entity )
                {
                    //always wrap entiry with detachableModel to ensure that it can be accessed with current Unit of Work
                    return (T) new ChronosEntityModel( returnedValue );
                }
                else
                {
                    return (T) returnedValue;
                }
            }
            else
            {
                return (T) PropertyResolver.getValue( getPropertyExpression(), target );
            }
        }
        return null;
    }

    public final String getPropertyExpression()
    {
        return propertyExpression();
    }

    protected abstract String propertyExpression();

    public void setChainedModel( IModel<?> model )
    {
        target = model;
    }

    @SuppressWarnings( "unchecked" )
    public void setObject( Object object )
    {
        if( Strings.isEmpty( getPropertyExpression() ) )
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
                ChronosPropertyResolver.setValue( getPropertyExpression(), getTarget(), object );
            }
            else
            {
                PropertyResolverConverter prc = new PropertyResolverConverter( Application.get().getConverterLocator(),
                                                                               Session.get().getLocale() );
                PropertyResolver.setValue( getPropertyExpression(), getTarget(), object, prc );
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
