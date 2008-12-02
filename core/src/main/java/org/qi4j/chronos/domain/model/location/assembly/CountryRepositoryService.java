/*  Copyright 2008 Edward Yakop.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.domain.model.location.assembly;

import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.CountryRepository;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;
import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( CountryRepositoryService.CountryRepositoryMixin.class )
interface CountryRepositoryService extends CountryRepository, ServiceComposite
{
    class CountryRepositoryMixin
        implements CountryRepository
    {
        @Structure UnitOfWorkFactory uowf;

        public final Query<Country> findAll()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            QueryBuilderFactory qbf = uow.queryBuilderFactory();
            QueryBuilder<Country> qb = qbf.newQueryBuilder( Country.class );
            return qb.newQuery();
        }

        public final Country findByNumericCode( String aCode )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            try
            {
                return uow.find( aCode, Country.class );
            }
            catch( EntityCompositeNotFoundException e )
            {
                return null;
            }
        }
    }
}
