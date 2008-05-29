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
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkEntryEditPage extends WorkEntryAddEditPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( WorkEntryEditPage.class );

    public WorkEntryEditPage( Page basePage, IModel<WorkEntry> workEntry )
    {
        super( basePage, workEntry );
    }

    public void onSubmitting( IModel<WorkEntry> workEntry )
    {
        try
        {
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

            logInfoMsg( "WorkEntry was saved successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( "Fail to update workEntry" );

            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit WorkEntry";
    }
}
