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
package org.qi4j.chronos.service;

import java.util.Collection;
import java.util.List;
import org.qi4j.annotation.Mixins;
import org.qi4j.chronos.service.mocks.MockEntityServiceMixin;
import org.qi4j.persistence.EntityComposite;
import org.qi4j.persistence.Identity;

@Mixins( MockEntityServiceMixin.class )
public interface EntityService<T extends Identity>
{
    T get( String id );

    void save( T obj );

    void delete( String id );

    void delete( T obj );

    void delete( Collection<T> objs );

    void update( T obj );

    List<T> findAll();

    List<T> find( FindFilter findFilter );

    int countAll();

    T newInstance( Class<? extends EntityComposite> clazz );
}
