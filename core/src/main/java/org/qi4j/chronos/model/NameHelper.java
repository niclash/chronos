/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.model;

import java.util.Collection;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.scope.This;

@Mixins( NameHelper.NameHelperMixin.class )
public interface NameHelper
{
    boolean nameIsEqual( String name );

    boolean nameIsUnique( Collection<Name> names );

    static class NameHelperMixin implements NameHelper
    {
        private @This Name m_name;

        public boolean nameIsEqual( String name )
        {
            return name.equals( m_name.name().get() );
        }

        public boolean nameIsUnique( Collection<Name> names )
        {
            for( Name name : names )
            {
                if( nameIsEqual( name.name().get() ) )
                {
                    return false;
                }
            }
            return true;
        }
    }
}
