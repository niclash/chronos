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
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;

public interface ProjectRoleService
{
    List<ProjectRoleComposite> findAll( AccountEntityComposite account, FindFilter findFilter );

    List<ProjectRoleComposite> findAll( AccountEntityComposite account );

    int countAll( AccountEntityComposite account );

    ProjectRoleComposite get( AccountEntityComposite account, String projectRoleName );

    void deleteProjectRole( AccountEntityComposite account, Collection<ProjectRoleComposite> projectRoles );

    void updateProjectRole( AccountEntityComposite account, ProjectRoleComposite oldProjectRole, ProjectRoleComposite newProjectRole );
}
