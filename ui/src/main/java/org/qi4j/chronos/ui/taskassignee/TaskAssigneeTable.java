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
package org.qi4j.chronos.ui.taskassignee;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.TaskAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.service.TaskAssigneeService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;

public abstract class TaskAssigneeTable extends ActionTable<TaskAssigneeEntityComposite, String>
{
    private TaskAssigneeDataProvider dataProvider;

    public TaskAssigneeTable( String id )
    {
        super( id );

        addActions();
    }

    protected void authorizatiingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<TaskAssigneeEntityComposite>( "Delete" )
        {
            public void performAction( List<TaskAssigneeEntityComposite> taskAssignees )
            {
                getTaskAssigneeService().delete( taskAssignees );
            }
        } );
    }

    private TaskAssigneeService getTaskAssigneeService()
    {
        return ChronosWebApp.getServices().getTaskAssigneeService();
    }

    public AbstractSortableDataProvider<TaskAssigneeEntityComposite, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new TaskAssigneeDataProvider()
            {
                public TaskEntityComposite getTask()
                {
                    return TaskAssigneeTable.this.getTask();
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, TaskAssigneeEntityComposite obj )
    {
        item.add( createDetailLink( "firstName", obj.getProjectAssignee().getStaff().getFirstName(), obj.getIdentity() ) );
        item.add( createDetailLink( "lastName", obj.getProjectAssignee().getStaff().getLastName(), obj.getIdentity() ) );
    }

    private SimpleLink createDetailLink( String id, String text, final String taskAssigneeId )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
                TaskAssigneeDetailPage detailPage = new TaskAssigneeDetailPage( this.getPage() )
                {
                    public TaskAssigneeEntityComposite getTaskAssignee()
                    {
                        return getTaskAssigneeService().get( taskAssigneeId );
                    }
                };

                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "First Name", "Last Name" );
    }

    public abstract TaskEntityComposite getTask();
}
