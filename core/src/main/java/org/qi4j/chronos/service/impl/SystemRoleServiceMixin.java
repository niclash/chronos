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
package org.qi4j.chronos.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleTypeEnum;
import org.qi4j.chronos.service.SystemRoleService;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.query.QueryBuilder;
import static org.qi4j.query.QueryExpressions.eq;
import static org.qi4j.query.QueryExpressions.templateFor;

public abstract class SystemRoleServiceMixin extends AbstractServiceMixin
    implements SystemRoleService
{
    private static final SystemRole systemRoleTemplate = templateFor( SystemRole.class );

    public List<SystemRole> findAllSystemRoles()
    {
        UnitOfWork unitOfWork = getUnitOfWork();
        QueryBuilder<SystemRole> builder = unitOfWork.queryBuilderFactory().newQueryBuilder( SystemRole.class );

        List<SystemRole> systemRoles = new ArrayList<SystemRole>();
        for( SystemRole systemRole : builder.newQuery() )
        {
            systemRoles.add( systemRole );
        }
        return systemRoles;
    }

    public List<SystemRole> findAllStaffRoles()
    {
        UnitOfWork unitOfWork = getUnitOfWork();
        QueryBuilder<SystemRole> builder = unitOfWork.queryBuilderFactory().newQueryBuilder( SystemRole.class );

        builder.where( eq( systemRoleTemplate.systemRoleType(), SystemRoleTypeEnum.STAFF ) );
        
        List<SystemRole> staffRoles = new ArrayList<SystemRole>();
        for( SystemRole systemRole : builder.newQuery() )
        {
            staffRoles.add( systemRole );
        }
        return staffRoles;
    }
}
