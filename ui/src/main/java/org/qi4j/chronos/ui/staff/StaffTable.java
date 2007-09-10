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

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionAdapter;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.library.general.model.Money;

public class StaffTable extends ActionTable<StaffEntityComposite>
{
    private SimpleDataProvider<StaffEntityComposite> dataProvider;

    public StaffTable( String id )
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

        addAction( new ActionAdapter( "Disable Login" )
        {
            public void performAction( AbstractSortableDataProvider dataProvider )
            {
                //TODO bp. fixme
            }
        } );
    }

    public AbstractSortableDataProvider<StaffEntityComposite> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new SimpleDataProvider<StaffEntityComposite>()
            {
                public EntityService<StaffEntityComposite> getEntityService()
                {
                    return ChronosWebApp.getServices().getStaffService();
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, StaffEntityComposite obj )
    {
        final String staffId = obj.getIdentity();

        item.add( new SimpleLink( "firstName", obj.getFirstName() )
        {
            public void linkClicked()
            {
                //TODO bp. fixme
            }
        } );

        item.add( new SimpleLink( "lastName", obj.getLastName() )
        {
            public void linkClicked()
            {
                //TODO bp. fixme
            }
        } );

        Money salary = obj.getSalary();

        item.add( new Label( "salary", salary.getCurrency().getSymbol() + salary.getAmount() ) );

        item.add( new Label( "loginId", obj.getLogin().getName() ) );

        item.add( new SimpleCheckBox( "loginEnabled", obj.getLogin().isEnabled(), true ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                setResponsePage( new StaffEditPage( (BasePage) this.getPage() )
                {
                    public String getStaffId()
                    {
                        return staffId;
                    }
                } );
            }
        } );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "First Name", "Last name", "Salary", "Login Id", "Login Enabled", "Edit" );
    }
}
