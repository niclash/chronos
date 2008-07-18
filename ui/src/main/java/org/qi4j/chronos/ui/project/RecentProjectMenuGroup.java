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

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.ui.common.menu.MenuGroupPanel;
import org.qi4j.chronos.ui.common.menu.MenuItemPanel;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.entity.association.SetAssociation;

public final class RecentProjectMenuGroup extends MenuGroupPanel
{
    private static final long serialVersionUID = 1L;

    private final static int TOTAL_PROJECT_TO_SHOW = 5;

    public RecentProjectMenuGroup()
    {
        // TODO: Localization
        super( new Model<String>( "Recent Projects" ) );
    }

    @Override
    public List<MenuItemPanel> getMenuItemList()
    {
        SetAssociation<Project> projects = getUserProjects();
        return createRecentProjectMenuItems( projects );
    }

    private List<MenuItemPanel> createRecentProjectMenuItems( SetAssociation<Project> projects )
    {
        int noOfProjects = projects.size();
        int toShowSize = min( TOTAL_PROJECT_TO_SHOW, noOfProjects );
        List<MenuItemPanel> menuItems = new ArrayList<MenuItemPanel>( toShowSize );

        Iterator<Project> it = projects.iterator();
        for( int i = 0; i < toShowSize; i++ )
        {
            Project project = it.next();

            String projectId = project.identity().get();
            String projectName = project.name().get();
            menuItems.add( new ProjectMenuItemPanel( projectName, projectId ) );
        }
        return menuItems;
    }

    private SetAssociation<Project> getUserProjects()
    {
        ChronosSession session = (ChronosSession) getSession();
        Account account = session.getAccount();
        return account.projects();
    }

    private static final class ProjectMenuItemPanel extends MenuItemPanel
    {
        private static final long serialVersionUID = 1L;

        private final String projectId;

        private ProjectMenuItemPanel( String aProjectName, String aProjectId )
        {
            super( new Model<String>( aProjectName ) );
            projectId = aProjectId;
        }

        @Override
        protected final void handleClicked()
        {
            ProjectDetailPage detailPage = new ProjectDetailPage( getPage(), projectId );
            setResponsePage( detailPage );
        }
    }
}