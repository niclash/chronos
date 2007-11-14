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

import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;

public abstract class WorkEntryEditDialog extends WorkEntryAddEditDialog
{
    public WorkEntryEditDialog()
    {
        assignWorkEntryToFieldValue( getWorkEntry() );
    }

    public ProjectAssigneeEntityComposite getProjectAssignee()
    {
        return getWorkEntry().getProjectAssignee();
    }

    public String getOkButtonText()
    {
        return "Save";
    }

    public void handleOkClicked()
    {
        WorkEntryEntityComposite workEntry = getWorkEntry();

        //set values
        assignFieldValueToWorkEntry( workEntry );

        //update work entry
        getServices().getWorkEntryService().update( workEntry );
    }

    protected String getDialogTitle()
    {
        return "Edit Work Entry";
    }

    public abstract WorkEntryEntityComposite getWorkEntry();
}
