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

import java.util.List;
import java.util.ArrayList;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.menu.MenuBar;
import org.qi4j.chronos.ui.common.menu.MenuItem;
import org.qi4j.chronos.ui.common.menu.MenuLink;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.entity.Identity;

public abstract class RecentProjectMenuBar extends MenuBar
{
    private final static int TOTAL_PROJECT_TO_SHOW = 5;

    public RecentProjectMenuBar()
    {
        super( "Recent Projects" );
    }

    public abstract Account getAccount();

    public MenuItem[] getMenuItemList()
    {
//        ProjectService projectService = ChronosWebApp.getServices().getProjectService();

        // TODO migrate
//        int countAll = projectService.countAll( getAccount() );
        int countAll = getAccount().projects().size();
        
        int toShowSize = Math.min( TOTAL_PROJECT_TO_SHOW, countAll );

//        List<Project> recentProjectList = projectService.
//            getRecentProjects( getAccount(), new FindFilter( 0, toShowSize ) );

        List<Project> recentProjectList = new ArrayList<Project>( getAccount().projects() );
        MenuItem[] menuItems = new MenuItem[ recentProjectList.size() ];

        int index = 0;
        for( Project project : recentProjectList )
        {
            final String projectId = ( (Identity) project).identity().get();

            menuItems[ index ] = new MenuLink( project.name().get() )
            {
                protected void handleClicked()
                {
                    showProjectDetail( projectId );
                }
            };

            index++;
        }

        return menuItems;
    }

    private void showProjectDetail( final String projectId )
    {
        ProjectDetailPage detailPage = new ProjectDetailPage( (BasePage) this.getPage() )
        {
            public Project getProject()
            {
                // TODO kamil: migrate
//                return ChronosWebApp.getServices().getProjectService().get( projectId );
                for( Project project : getAccount().projects() )
                {
                    if( projectId.equals( ( (Identity) project).identity().get() ) )
                    {
                        return project;
                    }
                }

                return null;
            }
        };

        setResponsePage( detailPage );
    }
}
