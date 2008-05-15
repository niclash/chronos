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
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectRoleEditPage extends ProjectRoleAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectRoleEditPage.class );
    private static final String UPDATE_SUCCESSS = "updateSuccessful";
    private static final String UPDATE_FAIL = "updateFailed";
    private static final String SUBMIT_BUTTON = "editPageSubmitButton";
    private static final String TITLE_LABEL = "editPageTitleLabel";

    public ProjectRoleEditPage( Page goBackPage, final String projectRoleId )
    {
        super( goBackPage );

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    protected Object load()
                    {
                        return getUnitOfWork().find( projectRoleId, ProjectRoleEntityComposite.class );
                    }
                }
            )
        );

        bindPropertyModel( getModel() );
    }

    public String getSubmitButtonValue()
    {
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }

    public void onSubmitting()
    {
        try
        {
            getUnitOfWork().complete();
            logInfoMsg( getString( UPDATE_SUCCESSS ) );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            reset();

            logErrorMsg( getString( UPDATE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
        catch( Exception err )
        {
            LOGGER.error( err.getMessage(), err );
        }
    }
}
