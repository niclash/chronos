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
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskAddPage extends TaskAddEditPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( TaskAddPage.class );

    public TaskAddPage( Page basePage, IModel<Task> task )
    {
        super( basePage, task );
    }

//    private void bindModel()
//    {
//        setModel(
//            new CompoundPropertyModel(
//                new LoadableDetachableModel()
//                {
//                    protected Object load()
//                    {
//                        final Task task = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().newEntityBuilder( TaskEntityComposite.class ).newInstance();
//                        task.createdDate().set( new Date() );
//                        task.user().set( ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( TaskAddPage.this.getTaskOwner() ) );
//
//                        return task;
//                    }
//                }
//            )
//        );
//
//        bindPropertyModel( getModel() );
//    }

    public void onSubmitting()
    {
        try
        {
            final Task task = (Task) getModelObject();
            TaskAddPage.this.getProject().tasks().add( task );
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            logInfoMsg( "Task is added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

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
        return "Add Task";
    }

    public User getTaskOwner()
    {
        return getChronosSession().getUser();
    }

    public abstract Project getProject();
}
