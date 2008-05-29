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

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskEditPage extends TaskAddEditPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( TaskEditPage.class );

    public TaskEditPage( Page basePage, IModel<Task> task )
    {
        super( basePage, task );
    }

    public void onSubmitting( IModel<Task> model )
    {
        try
        {
            final Task task = model.getObject();
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            logInfoMsg( "Task is updated successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage() );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Task";
    }

    public User getTaskOwner()
    {
        return getTask().user().get();
    }

    public abstract Task getTask();
}
