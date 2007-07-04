/*
 * Copyright 2007 Lan Boon Ping. All Rights Reserved.
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

import java.util.List;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatus;

public interface AccountFinder
{
    /**
     * Find a list of project based on the given customerId and project status.
     *
     * @param customerId    - the target customerId
     * @param projectStatus - the project status. null if find all.
     * @param searchParam   - the search param. null if no searchParam.
     * @return a list of project.
     */
    List<Project> findProjects( String customerId, ProjectStatus projectStatus, SearchParam searchParam );

    List<PriceRateSchedule> findPriceRateSchedule( String accountId );

}

