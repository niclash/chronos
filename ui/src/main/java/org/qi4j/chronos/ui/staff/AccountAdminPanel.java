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
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.workentry.WorkEntryTab2;

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

        tabs.add( createWorkEntry() );
        tabs.add( createProjectTab() );
        tabs.add( createStaffTab() );

        tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

        add( tabbedPanel );
    }

    private ProjectService getProjectService()
    {
        return ChronosWebApp.getServices().getProjectService();
    }

    private StaffService getStaffService()
    {
        return ChronosWebApp.getServices().getStaffService();
    }

    private WorkEntryService getWorkEntryService()
    {
        return ChronosWebApp.getServices().getWorkEntryService();
    }

    private StaffTab createStaffTab()
    {
        StaffTab staffTab = new StaffTab( "Active Staff" )
        {
            public int getSize()
            {
                return getStaffService().countAll( getAccount() );
            }

            public List<StaffEntityComposite> dataList( int first, int count )
            {
                return getStaffService().getRecentActiveStaff( getAccount(), new FindFilter( first, count ) );
            }
        };

        return staffTab;
    }

    private WorkEntryTab2 createWorkEntry()
    {
        return new WorkEntryTab2( "Recent Work Entry" )
        {
            public List<WorkEntryEntityComposite> dataList( int first, int count )
            {
                return getWorkEntryService().getRecentWorkEntryList( getAccount(), new FindFilter( first, count ) );
            }

            public int getSize()
            {
                return getWorkEntryService().countAll( getAccount() );
            }
        };
    }

    private ProjectTab createProjectTab()
    {
        return new ProjectTab( "Recent Projects" )
        {
            public int getSize()
            {
                return getProjectService().countAll( getAccount() );
            }

            public List<ProjectEntityComposite> dataList( int first, int count )
            {
                return getProjectService().getRecentProjectList( getAccount(), new FindFilter( first, count ) );
            }
        };
    }

    public abstract AccountEntityComposite getAccount();
}
