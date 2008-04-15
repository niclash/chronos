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
package org.qi4j.chronos.ui.task;

import java.util.Arrays;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.TaskStatusEnum;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextArea;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.SimpleTextField;

public abstract class TaskAddEditPage extends AddEditBasePage
{
    private MaxLengthTextField titleField;
    private MaxLengthTextArea descriptionTextArea;
    private SimpleTextField userField;
    private SimpleDropDownChoice<TaskStatusEnum> taskStatusChoice;

    public TaskAddEditPage( Page basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        titleField = new MaxLengthTextField( "titleField", "Title", Task.TITLE_LEN );
        descriptionTextArea = new MaxLengthTextArea( "descriptionTextArea", "Description", Task.DESCRIPTION_LEN );

        userField = new SimpleTextField( "userField", getTaskOwner().fullName().get(), true );

        taskStatusChoice = new SimpleDropDownChoice<TaskStatusEnum>( "taskStatusChoice", Arrays.asList( TaskStatusEnum.values() ), true );

        form.add( titleField );
        form.add( descriptionTextArea );
        form.add( userField );
        form.add( taskStatusChoice );
    }

    protected void assignFieldValueToTaskMaster( Task task )
    {
        task.description().set( descriptionTextArea.getText() );
        task.title().set( titleField.getText() );
        task.user().set( getTaskOwner() );
        task.taskStatus().set( taskStatusChoice.getChoice() );
    }

    protected void assignTaskMasterToFieldValie( Task task )
    {
        descriptionTextArea.setText( task.description().get() );
        titleField.setText( task.title().get() );
        taskStatusChoice.setChoice( task.taskStatus().get() );
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( titleField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( descriptionTextArea.checkIsInvalidLength() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public abstract User getTaskOwner();

    public abstract void onSubmitting();
}
