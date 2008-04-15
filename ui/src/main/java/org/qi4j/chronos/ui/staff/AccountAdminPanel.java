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
package org.qi4j.chronos.ui.staff;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.task.RecentTaskTab;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;

public abstract class AccountAdminPanel extends Panel
{
    private TabbedPanel tabbedPanel;

    public AccountAdminPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        List<AbstractTab> tabs = new ArrayList<AbstractTab>();

        tabs.add( createRecentTaskTab() );
        tabs.add( createRecentProjectTab() );

        tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

        add( tabbedPanel );
    }

    private TaskService getTaskService()
    {
        return ChronosWebApp.getServices().getTaskService();
    }

    private RecentTaskTab createRecentTaskTab()
    {
        return new RecentTaskTab( "Recent Tasks" )
        {
            public int getSize()
            {
                return getTaskService().countRecentTasks( getAccount() );
            }

            public List<Task> dataList( int first, int count )
            {
                return getTaskService().getRecentTasks( getAccount(), new FindFilter( first, count ) );
            }
        };
    }

    private ProjectService getProjectService()
    {
        return ChronosWebApp.getServices().getProjectService();
    }

    private ProjectTab createRecentProjectTab()
    {
        return new ProjectTab( "Recent Projects" )
        {
            public int getSize()
            {
                return getProjectService().countRecentProject( getAccount() );
            }

            public List<Project> dataList( int first, int count )
            {
                return getProjectService().getRecentProjects( getAccount(), new FindFilter( first, count ) );
            }
        };
    }

    public abstract Account getAccount();
}
