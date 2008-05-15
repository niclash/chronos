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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WorkEntryAddPage extends WorkEntryAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( WorkEntryAddPage.class );
    private static final String ADD_FAIL = "addFailed";
    private static final String ADD_SUCCESS = "addSuccessful";
    private static final String SUBMIT_BUTTON = "addPageSubmitButton";
    private static final String TITLE_LABEL = "addPageTitleLabel";

    public WorkEntryAddPage( Page basePage )
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
                    public Object load()
                    {
                        final UnitOfWork unitOfWork = getUnitOfWork();
                        final ProjectAssignee projectAssignee =
                            unitOfWork.dereference( WorkEntryAddPage.this.getProjectAssignee() );
                        final WorkEntry workEntry =
                            unitOfWork.newEntityBuilder( WorkEntryEntityComposite.class ).newInstance();
                        workEntry.createdDate().set( new Date() );
                        workEntry.projectAssignee().set( projectAssignee );

                        return workEntry;
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
            final WorkEntry workEntry = (WorkEntry) getModelObject();
            final HasWorkEntries hasWorkEntries = unitOfWork.dereference( WorkEntryAddPage.this.getHasWorkEntries() );
            hasWorkEntries.workEntries().add( workEntry );
            unitOfWork.complete();
            logInfoMsg( getString( ADD_SUCCESS ) );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            unitOfWork.reset();

            logErrorMsg( getString( ADD_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
        catch( Exception err )
        {
            unitOfWork.reset();

            logErrorMsg( getString( ADD_FAIL, new Model( err ) ) );
            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }

    public abstract ProjectAssignee getProjectAssignee();

    public abstract HasWorkEntries getHasWorkEntries();
}
