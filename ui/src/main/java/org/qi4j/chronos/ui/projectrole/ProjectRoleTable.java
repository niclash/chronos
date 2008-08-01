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

import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.associations.HasProjectRoles;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProjectRoleTable extends ActionTable<ProjectRole>
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger( ProjectRoleTable.class );

    private static final String DELETE_FAIL = "deleteFailed";
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";

    private static final String[] COLUMN_NAMES = { "Name", "" };

    public ProjectRoleTable( String id, IModel<? extends HasProjectRoles> hasProjectRoles, ProjectRoleDataProvider dataProvider )
    {
        super( id, hasProjectRoles, dataProvider, COLUMN_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new DeleteAction<ProjectRole>( getString( DELETE_ACTION ) )
            {
                private static final long serialVersionUID = 1L;

                public void performAction( List<ProjectRole> projectRoles )
                {
                    handleDeleteAction( projectRoles );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
    }

    private void handleDeleteAction( List<ProjectRole> projectRoles )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();

        try
        {
            HasProjectRoles hasProjectRoles = (HasProjectRoles) getDefaultModelObject();

            for( ProjectRole projectRole : projectRoles )
            {
                hasProjectRoles.projectRoles().remove( projectRole );
            }

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    private SimpleLink createEditLink( final String projectRoleId )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                //TODO
//                ProjectRoleEditPage roleEditPage = new ProjectRoleEditPage( getBasePage(), projectRoleId );
//
//                setResponsePage( roleEditPage );
            }
        };
    }

    public void populateItems( Item<ProjectRole> item )
    {
        ProjectRole projectRole = item.getModelObject();
        final String projectRoleName = projectRole.name().get();
        final String projectId = projectRole.identity().get();

        item.add( new Label( "roleName", projectRoleName ) );

        SimpleLink editLink = createEditLink( projectId );
        item.add( editLink );
    }
}
