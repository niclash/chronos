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
import java.util.Collections;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.task.RecentTaskTab;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;

public abstract class DeveloperPanel extends Panel
{
    private TabbedPanel tabbedPanel;

    public DeveloperPanel( String id )
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

    private ProjectTab createRecentProjectTab()
    {
        return new ProjectTab( "Recent Projects" )
        {
            public HasProjects getHasProjects()
            {
                return DeveloperPanel.this.getHasProjects();
            }

            public int getSize()
            {
//                return getProjectService().countRecentProject( getStaff() );
                return 0;
            }

            public List<String> dataList( int first, int count )
            {
/*
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
*/
                return Collections.EMPTY_LIST;
//                return getProjectService().getRecentProjects( getStaff(), new FindFilter( first, count ) );
            }
        };
    }

    // TODO kamil: fix business logic
    private RecentTaskTab createRecentTaskTab()
    {
        return new RecentTaskTab( "Recent Tasks" )
        {
            public int getSize()
            {
//                return getTaskService().countRecentTasks( getStaff() );
                return 0;
            }

            public List<String> dataList( int first, int count )
            {
//                return getTaskService().getRecentTasks( getStaff(), new FindFilter( first, count ) );
/*
                List<String> taskIdList = new ArrayList<String>();
                for( Task task : getTaskService().getRecentTasks( getStaff() ) )
                {
                    taskIdList.add( ( (Identity) task ).identity().get() );
                }
                return taskIdList.subList( first, first + count );
*/
                return Collections.EMPTY_LIST;
            }
        };
    }

    public abstract UnitOfWork getUnitOfWork();
    
    public abstract Staff getStaff();

    public abstract HasProjects getHasProjects();
}
