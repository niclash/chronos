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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.projectassignee.ProjectAssigneeDelegator;

public abstract class TaskAssigneeAddEditPage extends AddEditBasePage
{
    private SimpleDropDownChoice<ProjectAssigneeDelegator> projectAssigneeStaffChoice;

    public TaskAssigneeAddEditPage( BasePage basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        List<ProjectAssigneeDelegator> list = getProjectAssigneeDelegatorList();

        projectAssigneeStaffChoice = new SimpleDropDownChoice( "projectAssigneeStaffChoice", list, true );

        form.add( projectAssigneeStaffChoice );
    }

    private List<ProjectAssigneeDelegator> getProjectAssigneeDelegatorList()
    {
        List<ProjectAssigneeDelegator> results = new ArrayList<ProjectAssigneeDelegator>();

        List<ProjectAssigneeEntityComposite> list = getAvailableProjectAssigneeList();

        for( ProjectAssigneeEntityComposite projectAssignee : list )
        {
            results.add( new ProjectAssigneeDelegator( projectAssignee ) );
        }

        return results;
    }

    protected void assignFieldValuesToTaskAssignee( TaskAssigneeEntityComposite taskAssignee )
    {
        taskAssignee.setProjectAssignee( getSelectedProjectAssignee() );
    }

    protected void assignTaskAssigneeToFieldValues( TaskAssigneeEntityComposite taskAssignee )
    {
        projectAssigneeStaffChoice.setChoice( new ProjectAssigneeDelegator( taskAssignee.getProjectAssignee() ) );
    }

    private ProjectAssigneeEntityComposite getSelectedProjectAssignee()
    {
        return getServices().getProjectAssigneeService().get( projectAssigneeStaffChoice.getChoice().getId() );
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        //TODO bp. any validation?

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public abstract void onSubmitting();

    public abstract List<ProjectAssigneeEntityComposite> getAvailableProjectAssigneeList();
}
