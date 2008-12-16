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
package org.qi4j.chronos.domain.model.project.role.assembly;

import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleId;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleRepository;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.unitofwork.EntityCompositeNotFoundException;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.query.Query;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.query.QueryBuilderFactory;
import org.qi4j.api.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( ProjectRoleRepositoryService.ProjectRoleRepositoryMixin.class )
interface ProjectRoleRepositoryService extends ProjectRoleRepository, ServiceComposite
{
    class ProjectRoleRepositoryMixin
        implements ProjectRoleRepository
    {
        @Structure private UnitOfWorkFactory uowf;

        public final Query<ProjectRole> findAll()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilderFactory qbf = uow.queryBuilderFactory();
            QueryBuilder<ProjectRole> builder = qbf.newQueryBuilder( ProjectRole.class );
            return builder.newQuery();
        }

        public final ProjectRole find( ProjectRoleId roleId )
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            try
            {
                return uow.find( roleId.idString(), ProjectRole.class );
            }
            catch( EntityCompositeNotFoundException e )
            {
                return null;
            }
        }
    }
}
