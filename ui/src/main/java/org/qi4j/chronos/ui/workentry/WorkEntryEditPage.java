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

import org.apache.wicket.Page;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosWebApp;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WorkEntryEditPage extends WorkEntryAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( WorkEntryEditPage.class );

    public WorkEntryEditPage( Page basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        assignWorkEntryToFieldValue( getWorkEntry() );
    }

    public void onSubmitting()
    {
        // TODO kamil: use UnitOfWork
        UnitOfWork unitOfWork = getUnitOfWork();
        WorkEntry workEntry = getWorkEntry();

        try
        {
            assignFieldValueToWorkEntry( workEntry );

//            WorkEntryService service = getServices().getWorkEntryService();
//
//            service.update( workEntry );

            unitOfWork.complete();

            logInfoMsg( "Work Entry is updated successfully." );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            logErrorMsg( "Unable to update work entry!!!" + uowce.getClass().getSimpleName() );

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
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Work Entry";
    }

    public abstract WorkEntry getWorkEntry();
}
