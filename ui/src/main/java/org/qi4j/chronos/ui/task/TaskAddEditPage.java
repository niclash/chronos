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
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.TaskStatusEnum;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public abstract class TaskAddEditPage extends AddEditBasePage<Task>
{
    private static final long serialVersionUID = 1L;

    public TaskAddEditPage( Page basePage, IModel<Task> task )
    {
        super( basePage, task );
    }

    public void initComponent( Form<Task> form )
    {
        ChronosCompoundPropertyModel<Task> model = (ChronosCompoundPropertyModel<Task>) form.getModel();

        DropDownChoice taskStatusChoice = new DropDownChoice( "taskStatusChoice", Arrays.asList( TaskStatusEnum.values() ) );

        RequiredTextField<String> titleField = new RequiredTextField<String>( "title" );
        TextArea<String> descriptionTextArea = new TextArea<String>( "description" );

        TextField<String> userField = new TextField<String>( "user" );

        taskStatusChoice.setModel( model.bind( "taskStatus" ) );
        userField.setModel( model.<String>bind( "user.fullName" ) );
        descriptionTextArea.setModel( model.<String>bind( "description" ));

        form.add( titleField );
        form.add( descriptionTextArea );
        form.add( userField );
        form.add( taskStatusChoice );
    }

    public void handleSubmitClicked( IModel<Task> task )
    {
        onSubmitting( task );
    }

    public abstract User getTaskOwner();

    public abstract void onSubmitting( IModel<Task> task );
}
