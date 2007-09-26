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
package org.qi4j.chronos.ui.projectrole;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectRoleEntityComposite;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionAdapter;
import org.qi4j.chronos.ui.common.action.ActionTable;

public abstract class ProjectRoleTable extends ActionTable<ProjectRoleEntityComposite, String>
{
    private ProjectRoleDataProvider roleDataProvider;

    public ProjectRoleTable( String id )
    {
        super( id );

        initActions();
    }

    private void initActions()
    {
        addAction( new ActionAdapter( "Delete" )
        {
            public void performAction( AbstractSortableDataProvider dataProvider )
            {
                //TODO bp. fixme
            }
        } );
    }

    public AbstractSortableDataProvider<ProjectRoleEntityComposite, String> getDetachableDataProvider()
    {
        if( roleDataProvider == null )
        {
            roleDataProvider = new ProjectRoleDataProvider()
            {
                public AccountEntityComposite getAccount()
                {
                    return ProjectRoleTable.this.getAccount();
                }
            };
        }

        return roleDataProvider;
    }

    public void populateItems( Item item, ProjectRoleEntityComposite obj )
    {
        final String roleId = obj.getIdentity();

        item.add( new Label( "roleName", obj.getProjectRole() ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                ProjectRoleEditPage roleEditPage = new ProjectRoleEditPage( getBasePage(), roleId )
                {
                    public AccountEntityComposite getAccount()
                    {
                        return ProjectRoleTable.this.getAccount();
                    }
                };

                setResponsePage( roleEditPage );
            }
        } );
    }

    private BasePage getBasePage()
    {
        return (BasePage) this.getPage();
    }


    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "" );
    }

    public abstract AccountEntityComposite getAccount();
}
