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

import org.qi4j.chronos.domain.model.common.description.DescriptionState;
import org.qi4j.chronos.domain.model.common.name.NameState;
import org.qi4j.chronos.domain.model.project.role.ProjectRole;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleExistsException;
import org.qi4j.chronos.domain.model.project.role.ProjectRoleFactory;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.Optional;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.injection.scope.Structure;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import static org.qi4j.query.QueryExpressions.*;
import org.qi4j.query.grammar.EqualsPredicate;
import org.qi4j.query.grammar.VariableValueExpression;
import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
@Mixins( ProjectRoleFactoryService.ProjectRoleFactoryMixin.class )
interface ProjectRoleFactoryService extends ProjectRoleFactory, ServiceComposite
{
    class ProjectRoleFactoryMixin
        implements ProjectRoleFactory
    {
        private static EqualsPredicate<String> PREDICATE_ROLE_NAME;
        private static final String VARIABLE_ROLE_NAME = "roleName";

        static
        {
            VariableValueExpression<String> projectRoleName = variable( VARIABLE_ROLE_NAME );
            PREDICATE_ROLE_NAME = eq( templateFor( NameState.class ).name(), projectRoleName );
        }

        @Structure private UnitOfWorkFactory uowf;

        public final ProjectRole create( String projectRoleName, @Optional String description )
            throws ProjectRoleExistsException
        {
            UnitOfWork uow = uowf.currentUnitOfWork();
            validateRoleNameUniqueness( projectRoleName, uow );
            return createProjectRole( projectRoleName, description, uow );
        }

        private ProjectRole createProjectRole( String projectRoleName, String roleDescription, UnitOfWork uow )
        {
            EntityBuilder<ProjectRole> builder = uow.newEntityBuilder( ProjectRole.class );
            builder.stateFor( NameState.class ).name().set( projectRoleName );
            builder.stateFor( DescriptionState.class ).description().set( roleDescription );
            return builder.newInstance();
        }

        private void validateRoleNameUniqueness( String projectRoleName, UnitOfWork uow )
            throws ProjectRoleExistsException
        {
            QueryBuilder<ProjectRole> builder = uow.queryBuilderFactory().newQueryBuilder( ProjectRole.class );
            builder.where( PREDICATE_ROLE_NAME );
            Query<ProjectRole> query = builder.newQuery();
            query.setVariable( VARIABLE_ROLE_NAME, projectRoleName );
            ProjectRole role = query.find();
            if( role != null )
            {
                throw new ProjectRoleExistsException( projectRoleName );
            }
        }
    }
}
