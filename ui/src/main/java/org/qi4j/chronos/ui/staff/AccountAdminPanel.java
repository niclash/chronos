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
import java.util.Arrays;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.task.RecentTaskTab;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
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

    private RecentTaskTab createRecentTaskTab()
    {
        return new RecentTaskTab( "Recent Tasks" )
        {
            public int getSize()
            {
                // TODO kamil: migrate
//                return getTaskService().countRecentTasks( getAccount() );
                return getAccount().projects().iterator().next().tasks().size();
            }

            public List<Task> dataList( int first, int count )
            {
                // TODO migrate
//                return getTaskService().getRecentTasks( getAccount(), new FindFilter( first, count ) );
                return new ArrayList<Task>( getAccount().projects().iterator().next().tasks() );
            }
        };
    }

    private ProjectTab createRecentProjectTab()
    {
        return new ProjectTab( "Recent Projects" )
        {
            public int getSize()
            {
                return getAccount().projects().size();
            }

            public List<IModel> dataList( int first, int count )
            {
                List<IModel> models = new ArrayList<IModel>();
                for( Project project : getAccount().projects() )
                {
                    final String projectId = ( (Identity) project).identity().get();
                    models.add(
                        new CompoundPropertyModel(
                            new LoadableDetachableModel()
                            {
                                public Object load()
                                {
                                    return getUnitOfWork().find( projectId, ProjectEntityComposite.class );
                                }
                            }
                        )
                    );
                }
                return models;
            }
        };
    }

    public abstract UnitOfWork getUnitOfWork();
    
    public abstract Account getAccount();
}
