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
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;

public interface ProjectService extends AccountBasedService<org.qi4j.chronos.model.Project>
{
    List<Project> getRecentProjects( Account account, FindFilter findFilter );

    List<Project> getRecentProjects( Staff staff );

    List<Project> getRecentProjects( Staff staff, FindFilter findFilter );

    int countRecentProject( Staff staff );

    List<Project> findAll( ContactPerson contactPerson, FindFilter findFilter );

    List<Project> findAll( ContactPerson contactPerson );

    int countAll( ContactPerson contactPerson );

    List<Project> findAll( Staff staff );

    List<Project> findAll( Staff staff, FindFilter findFilter );

    int countAll( Staff staff );

    int countRecentProject( Account account );

    Project getProjectByTask( Task task );

    void changeProjectStatus( ProjectStatusEnum projectStatus, Collection<Project> projects );

    int countAll( Account account, ProjectStatusEnum projectStatus );

    Project getProjectByName( Account account, String projectName );
}
