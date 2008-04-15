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
package org.qi4j.chronos.ui.task;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.Project;

public abstract class TaskTab extends NewLinkTab
{
    public TaskTab( String id )
    {
        super( id );
    }

    public NewLinkPanel getNewLinkPanel( String id )
    {
        return new TaskMasterNewLinkPanel( id );
    }

    private TaskService getTaskMasterService()
    {
        return ChronosWebApp.getServices().getTaskService();
    }

    private class TaskMasterNewLinkPanel extends NewLinkPanel
    {
        public TaskMasterNewLinkPanel( String id )
        {
            super( id );
        }

        public Panel getContent( String id )
        {
            return new TaskTable( id )
            {
                public int getSize()
                {
                    // TODO migrate
//                    return getTaskMasterService().countAll( getProject() );
                    return 0;
                }

                public List<Task> dataList( int first, int count )
                {
                    // TODO migrate
//                    return getTaskMasterService().findAll( getProject(), new FindFilter( first, count ) );
                    return new ArrayList<Task>(0);
                }
            };
        }

        public void newLinkOnClick()
        {
            TaskAddPage addPage = new TaskAddPage( this.getPage() )
            {
                public Project getProject()
                {
                    return TaskTab.this.getProject();
                }
            };
            
            setResponsePage( addPage );
        }


        public String getNewLinkText()
        {
            return "New Task";
        }
    }

    public abstract Project getProject();
}
