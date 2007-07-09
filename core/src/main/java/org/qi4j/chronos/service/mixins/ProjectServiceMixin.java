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
package org.qi4j.chronos.service.mixins;

import java.util.List;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.service.ProjectFinder;
import org.qi4j.chronos.service.SearchParam;

public class ProjectServiceMixin implements ProjectFinder
{
    public List<WorkEntry> findWorkEntrys( String projectId, String staffI, SearchParam searchParam )
    {
        //TODO
        return null;
    }

    public List<ProjectAssignee> findProjectAssignees( String projectId, SearchParam searchParam )
    {
        //TODO
        return null;
    }

    public List<ProjectAssignee> findActiveProjectAssignees( String projectId, SearchParam searchParam )
    {
        //TODO
        return null;
    }

    public List<ProjectAssignee> findInActiveProjectAssignees( String projectId, SearchParam searchParam )
    {
        //TODO
        return null;
    }

    public List<PriceRateSchedule> findPriceRateSchedules( String projectId )
    {
        //TODO
        return null;
    }

    public PriceRateSchedule getCurrentPriceRateSchedule( String projectId )
    {
        //TODO
        return null;
    }
}