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
package org.qi4j.chronos.domain.model.user.assembly;

import org.qi4j.chronos.domain.model.user.UserId;
import org.qi4j.chronos.domain.model.user.admin.Admin;
import org.qi4j.chronos.domain.model.user.admin.AdminRepository;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;
import static org.qi4j.query.QueryExpressions.eq;
import static org.qi4j.query.QueryExpressions.templateFor;
import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( AdminRepositoryService.AdminRepositoryMixin.class )
interface AdminRepositoryService extends AdminRepository, ServiceComposite
{
    class AdminRepositoryMixin
        implements AdminRepository
    {
        @Structure private UnitOfWorkFactory uowf;

        public final Admin find( UserId aUserId )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            try
            {
                return uow.find( aUserId.idString(), Admin.class );
            }
            catch( EntityCompositeNotFoundException e )
            {
                return null;
            }
        }

        public final Admin findByLoginName( String aLoginName )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();

            try
            {
                QueryBuilderFactory qbf = uow.queryBuilderFactory();
                QueryBuilder<Admin> qb = qbf.newQueryBuilder( Admin.class );
                UserState stateTemplate = templateFor( UserState.class );
                qb.where( eq( stateTemplate.loginName(), aLoginName ) );
                Query<Admin> query = qb.newQuery();
                return query.find();
            }
            catch( EntityCompositeNotFoundException e )
            {
                return null;
            }
        }
    }
}
