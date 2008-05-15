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

import java.util.Date;
import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskAddPage extends TaskAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( TaskAddPage.class );

    public TaskAddPage( Page basePage )
    {
        super( basePage );

        bindModel();
    }

    private void bindModel()
    {
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    protected Object load()
                    {
                        final UnitOfWork unitOfWork = getUnitOfWork();
                        final Task task = unitOfWork.newEntityBuilder( TaskEntityComposite.class ).newInstance();
                        task.createdDate().set( new Date() );
                        task.user().set( unitOfWork.dereference( TaskAddPage.this.getTaskOwner() ) );

                        return task;
                    }
                }
            )
        );

        bindPropertyModel( getModel() );
    }

    public void onSubmitting()
    {
        final UnitOfWork unitOfWork = getUnitOfWork();
        try
        {
            final Task task = (Task) getModelObject();
            TaskAddPage.this.getProject().tasks().add( task );
            unitOfWork.complete();
            logInfoMsg( "Task is added successfully." );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            unitOfWork.reset();

            logErrorMsg( "Unable to save task!!!" + uowce.getClass().getSimpleName() );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
        catch( Exception err )
        {
            unitOfWork.reset();

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
