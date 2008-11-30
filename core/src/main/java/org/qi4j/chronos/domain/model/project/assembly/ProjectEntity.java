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
package org.qi4j.chronos.domain.model.project.assembly;

import org.qi4j.chronos.domain.model.common.legalCondition.LegalCondition;
import org.qi4j.chronos.domain.model.common.priceRate.PriceRateSchedule;
import org.qi4j.chronos.domain.model.project.Project;
import org.qi4j.chronos.domain.model.project.ProjectDetail;
import org.qi4j.chronos.domain.model.project.ProjectId;
import org.qi4j.chronos.domain.model.project.ProjectState;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.Mixins;
import org.qi4j.entity.AggregateEntity;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.injection.scope.This;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( ProjectEntity.ProjectMixin.class )
interface ProjectEntity extends Project, AggregateEntity
{
    abstract class ProjectMixin
        implements Project
    {
        private final ProjectId projectId;

        @Structure private UnitOfWorkFactory uowf;
        @This private ProjectState state;
        @Structure private CompositeBuilderFactory cbf;
        private ProjectDetail detail;

        public ProjectMixin( @This Identity identity )
        {
            String projectIdString = identity.identity().get();
            projectId = new ProjectId( projectIdString );
        }

        public final ProjectId projectId()
        {
            return projectId;
        }

        public final ProjectDetail projectDetail()
        {
            if( detail == null )
            {
                CompositeBuilder<ProjectDetail> builder = cbf.newCompositeBuilder( ProjectDetail.class );
                builder.use( state );
                detail = builder.newInstance();
            }

            return detail;
        }

        public final Query<LegalCondition> legalConditions()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilder<LegalCondition> builder = uow.queryBuilderFactory().newQueryBuilder( LegalCondition.class );
            return builder.newQuery( state.legalConditions() );
        }

        public final Query<PriceRateSchedule> priceRateSchedules()
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            QueryBuilderFactory qbf = uow.queryBuilderFactory();
            QueryBuilder<PriceRateSchedule> builder = qbf.newQueryBuilder( PriceRateSchedule.class );
            return builder.newQuery( state.priceRateSchedules() );
        }

        public final boolean sameIdentityAs( Project other )
        {
            return other != null && projectId.sameValueAs( other.projectId() );
        }
    }
}