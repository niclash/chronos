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
package org.qi4j.chronos.service.lab;

import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.EntityComposite;
import org.qi4j.chronos.service.FindFilter;
import java.util.Collection;
import java.util.List;

public interface NTTService<T, K>
{
    T get( UnitOfWork uow, String id );

    void save( UnitOfWork uow, T obj ) throws UnitOfWorkCompletionException;

    void saveAll( UnitOfWork uow, Collection<T> objs ) throws UnitOfWorkCompletionException;

    void delete( UnitOfWork uow, String id ) throws UnitOfWorkCompletionException;

    void delete( UnitOfWork uow, T obj ) throws UnitOfWorkCompletionException;

    void deleteAll( UnitOfWork uow, Collection<T > objs ) throws UnitOfWorkCompletionException;

    List<T> findAll( UnitOfWork uow );

    List<T> find( UnitOfWork uow, FindFilter findFilter );

    int countAll();

    K newInstance( UnitOfWork uow, Class<K > clazz );

    K newInstance( UnitOfWork uow );
}
