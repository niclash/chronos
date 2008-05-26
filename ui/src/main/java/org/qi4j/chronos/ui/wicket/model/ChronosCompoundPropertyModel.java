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

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.qi4j.entity.Entity;

/**
 * @author Lan Boon Ping
 */
public class ChronosCompoundPropertyModel<T> extends CompoundPropertyModel<T>
{
    private static final long serialVersionUID = 1L;

    public ChronosCompoundPropertyModel( T object )
    {
        super( object instanceof Entity ? new ChronosDetachableModel<T>( object ) : object );
    }

    public final <P> IModel<P> bind( String property )
    {
        return new ChronosPropertyModel<P>( this, property );
    }
}
