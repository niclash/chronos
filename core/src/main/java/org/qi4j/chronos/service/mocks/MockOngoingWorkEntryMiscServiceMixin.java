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
package org.qi4j.chronos.service.mocks;

import java.util.List;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.OngoingWorkEntryEntityComposite;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.OngoingWorkEntryService;
import org.qi4j.composite.scope.This;
import org.qi4j.entity.association.SetAssociation;

public abstract class MockOngoingWorkEntryMiscServiceMixin implements OngoingWorkEntryService
{
    @This OngoingWorkEntryService ongoingWorkEntryService;

    public OngoingWorkEntryEntityComposite getOngoingWorkEntry( TaskEntityComposite task, StaffEntityComposite staff )
    {
        List<OngoingWorkEntryEntityComposite> list = ongoingWorkEntryService.findAll( task );

        for( OngoingWorkEntryEntityComposite workEntry : list )
        {
            ProjectAssigneeEntityComposite projectAssignee = workEntry.projectAssignee().get();
            if( projectAssignee.staff().get().equals( staff ) )
            {
                return workEntry;
            }
        }

        return null;
    }

    public OngoingWorkEntryEntityComposite getOngoingWorkEntry( Project project, StaffEntityComposite staff )
    {
        SetAssociation<TaskEntityComposite> projectTasks = project.tasks();
        for( TaskEntityComposite taskEntityComposite : projectTasks )
        {
            TaskEntityComposite task = taskEntityComposite;

            OngoingWorkEntryEntityComposite workEntry = getOngoingWorkEntry( task, staff );

            if( workEntry != null )
            {
                return workEntry;
            }
        }

        return null;
    }
}
