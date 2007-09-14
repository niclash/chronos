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

import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryComposite;
import org.qi4j.chronos.service.ProjectAssigneeService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WorkEntryAddPage extends WorkEntryAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( WorkEntryAddPage.class );

    public WorkEntryAddPage( BasePage basePage )
    {
        super( basePage );
    }

    public void onSubmitting()
    {
        WorkEntryComposite entryComposite = ChronosWebApp.newInstance( WorkEntryComposite.class );

        try
        {
            assignFieldValueToWorkEntry( entryComposite );

            ProjectAssigneeEntityComposite projectAssignee = getProjectAssignee();

            projectAssignee.addWorkEntry( entryComposite );

            ProjectAssigneeService service = ChronosWebApp.getServices().getProjectAssigneeService();

            service.update( projectAssignee );

            logInfoMsg( "Work Entry is added successfully." );

            divertToGoBackPage();
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
}