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
package org.qi4j.chronos.ui.projectrole;

import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectRoleAddPage extends ProjectRoleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectRoleAddPage.class );
    private static final String ADD_FAIL = "addFailed";
    private static final String SUBMIT_BUTTON = "addPageSubmitButton";
    private static final String TITLE_LABEL = "addPageTitleLabel";
    private static final String ADD_SUCCESS = "addSuccessful";

    public ProjectRoleAddPage( Page goBackpage )
    {
        super( goBackpage );

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
                        final ProjectRole projectRole =
                            unitOfWork.newEntityBuilder( ProjectRoleEntityComposite.class ).newInstance();

                        return projectRole;
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
            final ProjectRole projectRole = (ProjectRole) getModelObject();
            final Account account = getAccount();
            account.projectRoles().add( projectRole );
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

            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
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
}
