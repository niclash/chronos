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
package org.qi4j.chronos.action.workentry;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.project.Project;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.workentry.WorkEntryCommentAddDialog;
import org.qi4j.chronos.workentry.WorkEntryListComponent;

public class WorkEntryNewCommentAction extends WorkEntryBasedAction
{
    public void execute( final WorkEntryListComponent workEntryListComponent, AnActionEvent e )
    {
        Project project = (Project) e.getDataContext().getData( DataConstants.PROJECT );

        WorkEntryCommentAddDialog addDialog = new WorkEntryCommentAddDialog( project )
        {
            public WorkEntry getWorkEntry()
            {
                return workEntryListComponent.getSelectedWorkEntry();
            }
        };

        addDialog.show();
    }
}
