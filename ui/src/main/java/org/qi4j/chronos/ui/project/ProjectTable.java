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
package org.qi4j.chronos.ui.project;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DefaultAction;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProjectTable extends ActionTable<Project>
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger( ProjectTable.class );
    private static final String DELETE_FAIL = "deleteFailed";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String UPDATE_FAIL = "updateFailed";

    private final static String[] COLUMN_NAMES = { "Name", "Formal Reference", "ActivationStatus", "" };

    public ProjectTable( String id, IModel<? extends HasProjects> hasProjects, ProjectDataProvider dataProvider )
    {
        super( id, hasProjects, dataProvider, COLUMN_NAMES );

        addActions();
    }

    protected void authorizingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    private void addActions()
    {
        addAction( new DeleteAction<Project>( "Delete" )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Project> projects )
            {
                handleDeleteAction( projects );
                info( getString( DELETE_SUCCESS ) );
            }
        } );

        addAction( new DefaultAction<Project>( "Change to active status" )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Project> projects )
            {
                handleStatusChangeAction( projects, ProjectStatusEnum.ACTIVE );
                info( "Selected project(s) are changed to status Active" );
            }
        } );

        addAction( new DefaultAction<Project>( "Change to inactive status" )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Project> projects )
            {
                handleStatusChangeAction( projects, ProjectStatusEnum.INACTIVE );
                info( "Selected project(s) are changed to inactive status" );
            }
        } );

        addAction( new DefaultAction<Project>( "Change to closed status " )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Project> projects )
            {
                handleStatusChangeAction( projects, ProjectStatusEnum.CLOSED );
                info( "Selected project(s) are changed to  Closed ActivationStatus" );
            }
        } );
    }

    private void handleDeleteAction( List<Project> projects )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();

        try
        {
            final HasProjects hasProjects = (HasProjects) getDefaultModelObject();

            for( Project project : projects )
            {
                hasProjects.projects().remove( project );
                unitOfWork.remove( project );
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

    private void handleStatusChangeAction( List<Project> projects, ProjectStatusEnum projectStatus )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            for( Project project : projects )
            {
                project.projectStatus().set( projectStatus );
            }
            unitOfWork.complete();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( UPDATE_FAIL ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    private SimpleLink createEditLink( final Project project )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
//                ProjectEditPage editPage =
//                    new ProjectEditPage( (BasePage) this.getPage(), iModel );
//                setResponsePage( editPage );
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }

    private SimpleLink createDetailLink( String id, String text, final IModel<Project> projectModel )
    {
        return new SimpleLink( id, text )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                ProjectDetailPage detailPage = new ProjectDetailPage( this.getPage(), projectModel );
                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "ActivationStatus", "" );
    }

    public void populateItems( Item<Project> item )
    {
        final Project project = item.getModelObject();
        final String projectId = project.identity().get();

        item.add( createDetailLink( "name", project.name().get(), item.getModel() ) );
        item.add( createDetailLink( "formalReference", project.reference().get(), item.getModel() ) );

        String projectStatus = project.projectStatus().get().name();
        item.add( new Label( "status", projectStatus) );

        final SimpleLink editLink = createEditLink( project );
        item.add( editLink );
    }

}
