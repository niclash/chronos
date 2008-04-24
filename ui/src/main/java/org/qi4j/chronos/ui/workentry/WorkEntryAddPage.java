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
package org.qi4j.chronos.ui.workentry;

import java.util.Date;
import org.apache.wicket.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosWebApp;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;

public abstract class WorkEntryAddPage extends WorkEntryAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( WorkEntryAddPage.class );

    public WorkEntryAddPage( Page basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
//        WorkEntry workEntry = getServices().getWorkEntryService().newInstance( WorkEntryEntityComposite.class );
        // TODO kamil: use UnitOfWork
        UnitOfWork unitOfWork = getUnitOfWork();

        try
        {
            WorkEntry workEntry = unitOfWork.newEntityBuilder( WorkEntryEntityComposite.class ).newInstance();
            workEntry.createdDate().set( new Date() );
            assignFieldValueToWorkEntry( workEntry );
            workEntry.projectAssignee().set( getProjectAssignee() );
            addingWorkEntry( workEntry );

            unitOfWork.complete();

            logInfoMsg( "Work Entry is added successfully." );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            logErrorMsg( "Unable to save work entry!!!" + uowce.getClass().getSimpleName() );

            if( null != unitOfWork && unitOfWork.isOpen() )
            {
                unitOfWork.discard();
            }

            LOGGER.error( uowce.getLocalizedMessage(), uowce );
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
        return "New Work Entry";
    }

    private UnitOfWork getUnitOfWork()
    {
        UnitOfWorkFactory unitOfWorkFactory = ChronosSession.get().getUnitOfWorkFactory();

        return null == unitOfWorkFactory.currentUnitOfWork() ? unitOfWorkFactory.newUnitOfWork() :
               unitOfWorkFactory.currentUnitOfWork();
    }
    
    public abstract ProjectAssignee getProjectAssignee();

    public abstract void addingWorkEntry( WorkEntry workentry );
}
