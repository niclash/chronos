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
package org.qi4j.chronos.ui.taskassignee;

import java.util.List;
import org.apache.wicket.Page;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.TaskAssigneeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskAssigneeAddPage extends TaskAssigneeAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( TaskAssigneeAddPage.class );

    public TaskAssigneeAddPage( Page basePage )
    {
        super( basePage );
    }

    private TaskAssigneeService getTaskAssigneeService()
    {
        return getServices().getTaskAssigneeService();
    }

    public void onSubmitting()
    {
        TaskAssigneeEntityComposite taskAssignee = getTaskAssigneeService().newInstance( TaskAssigneeEntityComposite.class );

        try
        {
            assignFieldValuesToTaskAssignee( taskAssignee );

            TaskEntityComposite task = getTask();

            task.addTaskAssignee( taskAssignee );

            logInfoMsg( "Task assignee is added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );

            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Task Assignee";
    }

    public List<ProjectAssigneeEntityComposite> getAvailableProjectAssigneeList()
    {
        return getServices().getProjectAssigneeService().getUnassignedProjectAssignee( getTask() );
    }

    public abstract TaskEntityComposite getTask();
}
