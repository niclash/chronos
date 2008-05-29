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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;
import org.qi4j.entity.Identity;

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
                    return TaskTab.this.getProject().tasks().size();
                }

                public List<String> dataList( int first, int count )
                {
                    List<String> taskIdList = new ArrayList<String>();
                    for( Task task : TaskTab.this.getProject().tasks() )
                    {
                        taskIdList.add( ( (Identity) task ).identity().get() );
                    }
                    return taskIdList.subList( first, first + count );
                }
            };
        }

        public void newLinkOnClick()
        {
            //TODO
//            TaskAddPage addPage = new TaskAddPage( this.getPage() )
//            {
//                public Project getProject()
//                {
//                    return TaskTab.this.getProject();
//                }
//            };
//
//            setResponsePage( addPage );
        }


        public String getNewLinkText()
        {
            return "New Task";
        }
    }

    public abstract Project getProject();
}
