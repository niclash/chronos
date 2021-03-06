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
package org.qi4j.chronos.task;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPopupMenu;
import com.intellij.openapi.project.Project;
import com.intellij.ui.PopupHandler;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.Component;
import java.awt.Container;
import java.util.List;
import javax.swing.ListSelectionModel;
import org.qi4j.chronos.action.ChronosActionConstant;
import org.qi4j.chronos.common.AbstractDialog;
import org.qi4j.chronos.common.ChronosDataProvider;
import org.qi4j.chronos.common.ChronosPageableWrapper;
import org.qi4j.chronos.common.ChronosTable;
import org.qi4j.chronos.common.ChronosTableModel;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.util.UiUtil;

public abstract class TaskViewAllDialog extends AbstractDialog
{
    private final static String[] COL_NAMES = { "Created Date", "Task Status", "Created By", "Title" };
    private final static int[] COL_WITDHS = { 150, 150, 150, 300 };

    private ChronosPageableWrapper<Task> pageableWrapper;

    public TaskViewAllDialog( Project project )
    {
        super( project, true );
    }

    protected void initComponents()
    {
        pageableWrapper = new ChronosPageableWrapper<Task>( createDataProvider(), COL_NAMES, COL_WITDHS )
        {
            protected ChronosTable createTable( String[] colNames, int[] colWidths )
            {
                TaskTable taskTable = new TaskTable( new ChronosTableModel( colNames ) );

                UiUtil.initTableWidth( taskTable, colWidths );

                taskTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

                return taskTable;
            }
        };
    }

    private class TaskTable extends ChronosTable
        implements TaskListComponent
    {
        public TaskTable( ChronosTableModel model )
        {
            super( model );

            initListeners();
        }

        private void initListeners()
        {
            PopupHandler popupHandler = new PopupHandler()
            {
                public void invokePopup( Component comp, int x, int y )
                {
                    ActionManager actionManager = getActionManager();

                    ActionGroup group = (ActionGroup) actionManager.getAction( ChronosActionConstant.TASK_LIST_GROUP );
                    ActionPopupMenu popupMenu = actionManager.createActionPopupMenu( "POPUP", group );

                    if( popupMenu != null )
                    {
                        popupMenu.getComponent().show( comp, x, y );
                    }
                }
            };

            this.addMouseListener( popupHandler );
        }

        public Task getSelectedTask()
        {
            return pageableWrapper.getSelectedItem();
        }

        public Task[] getSelectedTasks()
        {
            //bp. no multiple selection at this moment
            return new Task[]{ getSelectedTask() };
        }

        public void refreshList()
        {
            pageableWrapper.resetData();
        }

        public Container getComponent()
        {
            return this;
        }
    }

    private TaskService getTaskService()
    {
        return getServices().getTaskService();
    }

    private org.qi4j.chronos.model.Project getChronosProject()
    {
        return getChronosApp().getChronosProject();
    }

    private ChronosDataProvider<Task> createDataProvider()
    {
        return new ChronosDataProvider<Task>()
        {
            public List<Task> getData( int first, int count )
            {
                return getTaskService().findAll( getChronosProject(), new FindFilter( first, count ) );
            }

            public Object[] populateData( Task task )
            {
                return new Object[]{
                    DateUtil.formatDateTime( task.createdDate().get() ),
                    task.taskStatus().get().toString(),
                    task.user().get().fullName().get(),
                    task.title().get()
                };
            }

            public int getSize()
            {
                return getTaskService().countAll( getChronosProject() );
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
        builder.add( pageableWrapper, cc.xy( 1, 1, "fill,fill" ) );
    }

    protected String getDialogTitle()
    {
        return "View all tasks";
    }

    public abstract ActionManager getActionManager();
}
