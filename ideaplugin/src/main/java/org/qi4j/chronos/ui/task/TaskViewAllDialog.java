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

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.util.List;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.ui.common.AbstractDialog;
import org.qi4j.chronos.ui.common.ChronosDataProvider;
import org.qi4j.chronos.ui.common.ChronosPageableTable;
import org.qi4j.chronos.util.DateUtil;

public class TaskViewAllDialog extends AbstractDialog
{
    private final static String[] COL_NAMES = { "Created Date", "Task Status", "Created By", "Title" };
    private final static int[] COL_WITDHS = { 150, 150, 150, 300 };

    private ChronosPageableTable<TaskEntityComposite> table;

    public TaskViewAllDialog()
    {
        super( true );
    }

    protected void initComponents()
    {
        ChronosDataProvider dataProvider = createDataProvider();

        table = new ChronosPageableTable<TaskEntityComposite>( dataProvider, COL_NAMES, COL_WITDHS, 3 )
        {
            protected void rowOnDoubleClick( TaskEntityComposite o )
            {
                handleTaskSelected( o );
            }
        };
    }

    private void handleTaskSelected( final TaskEntityComposite task )
    {
        TaskEditDialog editDialog = new TaskEditDialog()
        {
            public TaskEntityComposite getTask()
            {
                return task;
            }
        };

        editDialog.show();

        //TODO bp. update the row to refresh the changes
    }

    private TaskService getTaskService()
    {
        return getServices().getTaskService();
    }

    private ProjectEntityComposite getProject()
    {
        return getChronosSetting().getChronosProject();
    }

    private ChronosDataProvider<TaskEntityComposite> createDataProvider()
    {
        return new ChronosDataProvider<TaskEntityComposite>()
        {
            public List<TaskEntityComposite> getData( int first, int count )
            {
                return getTaskService().findAll( getProject(), new FindFilter( first, count ) );
            }

            public Object[] populateData( TaskEntityComposite task )
            {
                Object[] objs = new Object[]{
                    DateUtil.formatDateTime( task.getCreatedDate() ),
                    task.getTaskStatus().toString(),
                    task.getUser().getFullname(),
                    task.getTitle()
                };

                return objs;
            }

            public int getSize()
            {
                return getTaskService().countAll( getProject() );
            }
        };
    }

    protected String getLayoutColSpec()
    {
        return "200dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "300dlu:grow";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( table, cc.xy( 1, 1, "fill,fill" ) );
    }

    protected String getDialogTitle()
    {
        return "View all tasks";
    }
}
