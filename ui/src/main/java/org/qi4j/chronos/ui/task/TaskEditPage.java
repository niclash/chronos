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
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.UnitOfWorkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskEditPage extends TaskAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( TaskEditPage.class );

    public TaskEditPage( Page basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        assignTaskMasterToFieldValie( getTask() );
    }

    public void onSubmitting()
    {
        // TODO kamil: edit task
        UnitOfWork unitOfWork = getUnitOfWork();
        Task taskMaster = getTask();

        try
        {
            assignFieldValueToTaskMaster( taskMaster );

            unitOfWork.complete();

            logInfoMsg( "Task is updated successfully." );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            logErrorMsg( "Unable to update task!!!" + uowce.getClass().getSimpleName() );

            if( null != unitOfWork && unitOfWork.isOpen() )
            {
                unitOfWork.discard();
            }

            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
        catch( Exception err )
        {
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
