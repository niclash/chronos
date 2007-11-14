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
package org.qi4j.chronos.workentry;

import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.WorkEntryService;

public abstract class WorkEntryAddDialog extends WorkEntryAddEditDialog
{
    protected String getDialogTitle()
    {
        return "New WorkEntry";
    }

    public void handleOkClicked()
    {
        WorkEntryService workEntryService = getServices().getWorkEntryService();

        WorkEntryEntityComposite workEntry = workEntryService.newInstance( WorkEntryEntityComposite.class );

        //set values
        assignFieldValueToWorkEntry( workEntry );

        //adding workEntry
        addingWorkEntry( workEntry );
    }

    public String getOkButtonText()
    {
        return "Add";
    }

    public User getWorkEntryOwner()
    {
        return getChronosSetting().getStaff();
    }

    public ProjectAssigneeEntityComposite getProjectAssignee()
    {
        return getChronosSetting().getProjectAssignee();
    }

    public abstract void addingWorkEntry( WorkEntryEntityComposite workEntry );
}
