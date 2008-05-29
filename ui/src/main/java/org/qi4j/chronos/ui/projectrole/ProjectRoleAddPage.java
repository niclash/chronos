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
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectRoleAddPage extends ProjectRoleAddEditPage
{
    private static final long serialVersionUID = 1L;


    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectRoleAddPage.class );

    public ProjectRoleAddPage( Page goBackpage, IModel<ProjectRole> model )
    {
        super( goBackpage, model );
    }

//    private void bindModel()
//    {
//        setModel(
//            new CompoundPropertyModel(
//                new LoadableDetachableModel()
//                {
//                    protected Object load()
//                    {
//                        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
//
//                        final ProjectRole projectRole =
//                            unitOfWork.newEntityBuilder( ProjectRoleEntityComposite.class ).newInstance();
//
//                        return projectRole;
//                    }
//                }
//            )
//        );
//        bindPropertyModel( getModel() );
//    }

    public void onSubmitting( IModel<ProjectRole> model )
    {
        try
        {
            final ProjectRole projectRole = model.getObject();
            final Account account = getAccount();
            account.projectRoles().add( projectRole );
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();

            logInfoMsg( "Project Role was added successfully." );
            divertToGoBackPage();
        }
        catch( Exception err )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

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
        return "New Project Role";
    }
}
