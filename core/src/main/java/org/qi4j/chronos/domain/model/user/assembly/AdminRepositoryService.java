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

import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import static org.qi4j.api.query.QueryExpressions.eq;
import static org.qi4j.api.query.QueryExpressions.templateFor;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.NoSuchEntityException;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.user.UserId;
import org.qi4j.chronos.domain.model.user.admin.Admin;
import org.qi4j.chronos.domain.model.user.admin.AdminRepository;

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
            catch( NoSuchEntityException e )
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
            catch( NoSuchEntityException e )
            {
                return null;
            }
        }
    }
}
