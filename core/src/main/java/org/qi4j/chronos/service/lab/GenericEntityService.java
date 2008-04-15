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
import org.qi4j.composite.Mixins;
import org.qi4j.chronos.service.FindFilter;
import java.util.Collection;
import java.util.List;

public interface GenericEntityService<K>
{
    K get( UnitOfWork unitOfWork, String entityId );

    /* void save( UnitOfWork uow, String id ) throws UnitOfWorkCompletionException; */

    void save( UnitOfWork unitOfWork, K entity ) throws UnitOfWorkCompletionException;

    void saveAll( UnitOfWork unitOfWork, Collection<K> entities ) throws UnitOfWorkCompletionException;

    /* void delete( UnitOfWork uow, String id ) throws UnitOfWorkCompletionException; */

    void delete( UnitOfWork unitOfWork, K entity ) throws UnitOfWorkCompletionException;

    void deleteAll( UnitOfWork unitOfWork, Collection<K> entities ) throws UnitOfWorkCompletionException;

    List<K> findAll( UnitOfWork unitOfWork );

    List<K> find( UnitOfWork unitOfWork, FindFilter findFilter );

    int countAll();

    K newInstance( UnitOfWork unitOfWork );
}