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
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WorkEntryAddPage extends WorkEntryAddEditPage
{
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = LoggerFactory.getLogger( WorkEntryAddPage.class );

    public WorkEntryAddPage( Page basePage, IModel<WorkEntry> workEntry )
    {
        super( basePage, workEntry );

    }
//
//    private void bindModel()
//    {
//        setModel(
//            new CompoundPropertyModel(
//                new LoadableDetachableModel()
//                {
//                    public Object load()
//                    {
//                        final ProjectAssignee projectAssignee =
//                            ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( WorkEntryAddPage.this.getProjectAssignee() );
//                        final WorkEntry workEntry =
//                            ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().newEntityBuilder( WorkEntryEntityComposite.class ).newInstance();
//                        workEntry.createdDate().set( new Date() );
//                        workEntry.projectAssignee().set( projectAssignee );
//
//                        return workEntry;
//                    }
//                }
//            )
//        );
//        bindPropertyModel( getModel() );
//    }

    public void onSubmitting()
    {
        try
        {
            final WorkEntry workEntry = (WorkEntry) getModelObject();
            final HasWorkEntries hasWorkEntries = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().dereference( WorkEntryAddPage.this.getHasWorkEntries() );
            hasWorkEntries.workEntries().add( workEntry );
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

            logInfoMsg( "Work Entry was added successfully." );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            logErrorMsg( "Fail to create new WorkEntry" );

            LOGGER.error( err.getLocalizedMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "New WorkEntry";
    }

    public abstract ProjectAssignee getProjectAssignee();

    public abstract HasWorkEntries getHasWorkEntries();
}
