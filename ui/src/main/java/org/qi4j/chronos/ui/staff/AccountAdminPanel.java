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
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.task.RecentTaskTab;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;

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

    // TODO kamil: fix business logic, should only return recent tasks
    private RecentTaskTab createRecentTaskTab()
    {
        return new RecentTaskTab( "Recent Tasks" )
        {
            public int getSize()
            {
                return getAccount().projects().iterator().next().tasks().size();
            }

            public List<String> dataList( int first, int count )
            {
                List<String> taskIdList = new ArrayList<String>();
                for( Task task : getAccount().projects().iterator().next().tasks() )
                {
                    taskIdList.add( ( (Identity) task ).identity().get() );
                }
                return taskIdList.subList( first, first + count );
            }
        };
    }

    private ProjectTab createRecentProjectTab()
    {
        return new ProjectTab( "Recent Projects" )
        {
            public HasProjects getHasProjects()
            {
                return AccountAdminPanel.this.getAccount();
            }

            public int getSize()
            {
                return getAccount().projects().size();
            }

            public List<String> dataList( int first, int count )
            {
                return AccountAdminPanel.this.dataList( first, count );
            }
        };
    }

    public List<String> dataList( int first, int count )
    {
        List<String> projectIdList = new ArrayList<String>();
        for( Project project : getAccount().projects() )
        {
            projectIdList.add( ( (Identity) project ).identity().get() );
        }
        return projectIdList.subList( first, first + count );
    }

    public abstract UnitOfWork getUnitOfWork();
    
    public abstract Account getAccount();
}
