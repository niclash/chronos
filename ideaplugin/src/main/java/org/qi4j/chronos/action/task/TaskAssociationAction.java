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
package org.qi4j.chronos.action.task;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import org.qi4j.chronos.common.AbstractPanel;
import org.qi4j.chronos.model.TaskStatus;
import org.qi4j.chronos.model.TaskStatusEnum;
import org.qi4j.chronos.model.Task;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.entity.Identity;

public class TaskAssociationAction extends AnAction
    implements CustomComponentAction
{
    private Project project;

    public TaskAssociationAction( Project project )
    {
        this.project = project;
    }

    public void actionPerformed( AnActionEvent e )
    {
        //nothing
    }

    public JComponent createCustomComponent( Presentation presentation )
    {
        return new TaskAssocaitionPanel();
    }

    private class TaskAssocaitionPanel extends AbstractPanel
    {
        private JComboBox taskComboBox;

        public TaskAssocaitionPanel()
        {
            init();
            setSelectedTask();
            initListener();
        }

        private void setSelectedTask()
        {
            //TODO bp.
        }

        protected String getLayoutColSpec()
        {
            return "p,p";
        }

        protected String getLayoutRowSpec()
        {
            return "p";
        }

        protected void initLayout( PanelBuilder builder, CellConstraints cc )
        {
            builder.addLabel( "<html><body><b>Chronos Task</b></body><html>", cc.xy( 1, 1 ) );

            builder.add( taskComboBox, cc.xy( 2, 1 ) );
        }

        protected void initComponents()
        {
            taskComboBox = new JComboBox( getOpenedTasks() );
        }

        public Object[] getOpenedTasks()
        {
            TaskService taskService = getServices( project ).getTaskService();

            List<Task> tasks = taskService.findTask( getChronosProject( project ), TaskStatusEnum.OPEN );

            List<TaskDelegator> list = new ArrayList<TaskDelegator>();

            for( Task task : tasks )
            {
                list.add( new TaskDelegator( task ) );
            }

            return list.toArray();
        }

        private void initListener()
        {
            taskComboBox.addItemListener( new ItemListener()
            {
                public void itemStateChanged( ItemEvent e )
                {
                    handleItemStateChanged();
                }
            } );
        }

        private void handleItemStateChanged()
        {
            TaskDelegator delegator = (TaskDelegator) taskComboBox.getSelectedItem();

            Task task = getServices( project ).getTaskService().get( delegator.getId() );

            getChronosApp( project ).setAssociatedTask( task );
        }
    }

    private class TaskDelegator
    {
        private String title;
        private String id;

        public TaskDelegator( Task task )
        {
            this.id = ((Identity) task).identity().get();
            this.title = task.title().get();
        }

        public String getId()
        {
            return id;
        }

        public String toString()
        {
            return title;
        }

        public boolean equals( Object o )
        {
            if( this == o )
            {
                return true;
            }
            if( o == null || getClass() != o.getClass() )
            {
                return false;
            }

            TaskDelegator that = (TaskDelegator) o;

            if( id != null ? !id.equals( that.id ) : that.id != null )
            {
                return false;
            }

            return true;
        }

        public int hashCode()
        {
            return ( id != null ? id.hashCode() : 0 );
        }
    }
}
