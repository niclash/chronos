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

import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.qi4j.entity.Entity;

/**
 * @author Lan Boon Ping
 */
public class ChronosCompoundPropertyModel<T> extends CompoundPropertyModel<T>
{
    private static final long serialVersionUID = 1L;

    public ChronosCompoundPropertyModel( T object )
    {
        super( object instanceof Entity ? new ChronosEntityModel<T>( object ) : object );
    }

    @Override
    public final <P> IModel<P> bind( String property )
    {
        return new ChronosPropertyModel<P>( this, property );
    }

    @Override
    public final <C> IWrapModel<C> wrapOnInheritance( Component component )
    {
        return new ChronosAttachedCompoundPropertyModel<C>( component );
    }

    private class ChronosAttachedCompoundPropertyModel<C> extends AbstractChronosPropertyModel<C>
        implements IWrapModel<C>
    {
        private static final long serialVersionUID = 1L;
        private final Component owner;

        public ChronosAttachedCompoundPropertyModel( Component owner )
        {
            super( ChronosCompoundPropertyModel.this );
            this.owner = owner;
        }

        public IModel<?> getWrappedModel()
        {
            return ChronosCompoundPropertyModel.this;
        }

        @Override protected String propertyExpression()
        {
            return ChronosCompoundPropertyModel.this.propertyExpression( owner );
        }

        @Override public void detach()
        {
            super.detach();
            ChronosCompoundPropertyModel.this.detach();
        }
    }
}
