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
package org.qi4j.chronos.ui.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.associations.HasProjects;

public class ProjectUtil
{
    public static final Map<ProjectStatusEnum, Integer> getProjectStatusCount( HasProjects hasProjects )
    {
        Map<ProjectStatusEnum, Integer> resultMap = new ConcurrentHashMap<ProjectStatusEnum, Integer>();

        for( ProjectStatusEnum projectStatus : ProjectStatusEnum.values() )
        {
            resultMap.put( projectStatus, 0 );
        }

        for( Project project : hasProjects.projects() )
        {
            ProjectStatusEnum projectStatus = project.projectStatus().get();

            int result = resultMap.get( projectStatus );
            resultMap.put( projectStatus, ++result );
        }

        return resultMap;
    }
}
