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
package org.qi4j.chronos;

import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import org.qi4j.chronos.model.TaskStatus;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.TaskService;
import org.qi4j.chronos.common.AbstractPanel;
import org.qi4j.chronos.common.text.ReadOnlyTextField;
import org.qi4j.chronos.workentry.WorkEntryTablePanel;
import org.qi4j.chronos.util.ChronosUtil;

public class ChronosToolContentPanel extends AbstractPanel
{
    private final static String NONE = "-- None -- ";

    private WorkEntryTablePanel workEntryTablePanel;

    private ReadOnlyTextField projectNameField;
    private JComboBox assocaitedTaskCombo;

    private Project project;

    public ChronosToolContentPanel( Project project )
    {
        this.project = project;

        init();

        initData();
    }

    private void initData()
    {
        //TODO set associatedTask
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 1dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p,3dlu,p,3dlu,p," +
               "3dlu,1dlu:grow";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.setDefaultDialogBorder();

        builder.addLabel( "Project", cc.xy( 1, 1 ) );
        builder.add( projectNameField, cc.xy( 3, 1 ) );

        builder.addLabel( "Associated Task", cc.xy( 1, 3 ) );
        builder.add( assocaitedTaskCombo, cc.xy( 3, 3 ) );

        builder.addLabel( "Recent WorkEntries", cc.xy( 1, 5 ) );
        builder.add( workEntryTablePanel, cc.xyw( 1, 7, 3, "fill,fill" ) );
    }

    private ProjectEntityComposite getChronosProject()
    {
        return ChronosUtil.getChronosSetting( project ).getChronosProject();
    }

    protected void initComponents()
    {
        workEntryTablePanel = new WorkEntryTablePanel( project );

        projectNameField = new ReadOnlyTextField( getChronosProject().getName() );
        assocaitedTaskCombo = new JComboBox( getAvailableOptions() );

        initListeners();
    }

    private void initListeners()
    {
        assocaitedTaskCombo.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                taskChanged();
            }
        } );
    }

    private void taskChanged()
    {
        //TODO bp. Remove the OngoingWorkEntry
    }

    @SuppressWarnings( "unchecked" )
    private Object[] getAvailableOptions()
    {
        List list = new ArrayList();

        //add none option
        list.add( NONE );

        TaskService taskService = ChronosUtil.getChronosSetting( project ).getServices().getTaskService();

        List<TaskEntityComposite> tasks = taskService.findTask( getChronosProject(), TaskStatus.OPEN );

        for( TaskEntityComposite task : tasks )
        {
            //add open task option
            list.add( new TaskDelegator( task ) );
        }

        return list.toArray( new Object[list.size()] );
    }

    private class TaskDelegator
    {
        private String title;
        private String id;

        public TaskDelegator( TaskEntityComposite task )
        {
            this.id = task.getIdentity();
            this.title = task.getTitle();
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
