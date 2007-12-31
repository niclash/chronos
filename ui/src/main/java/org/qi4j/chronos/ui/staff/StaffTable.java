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
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.library.general.model.Money;

public abstract class StaffTable extends ActionTable<StaffEntityComposite, String>
{
    private StaffDataProvider dataProvider;

    public StaffTable( String id )
    {
        super( id );

        initActions();
    }

    private void initActions()
    {
        addAction( new SimpleDeleteAction<StaffEntityComposite>( "Delete Staff" )
        {
            public void performAction( List<StaffEntityComposite> staffs )
            {
                getStaffService().delete( staffs );

                info( "Selected staff(s) are deleted." );
            }
        } );

        addAction( new SimpleAction<StaffEntityComposite>( "Disable Login" )
        {
            public void performAction( List<StaffEntityComposite> staffs )
            {
                getStaffService().enableLogin( false, staffs );

                info( "Selected staff(s) are disabled login." );
            }
        } );

        addAction( new SimpleAction<StaffEntityComposite>( "Enable Login" )
        {
            public void performAction( List<StaffEntityComposite> staffs )
            {
                getStaffService().enableLogin( true, staffs );

                info( "Selected staff(s) are enabled login." );
            }
        } );
    }

    private StaffService getStaffService()
    {
        return ChronosWebApp.getServices().getStaffService();
    }

    public AbstractSortableDataProvider<StaffEntityComposite, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new StaffDataProvider()
            {
                public int getSize()
                {
                    return StaffTable.this.getSize();
                }

                public List<StaffEntityComposite> dataList( int first, int count )
                {
                    return StaffTable.this.dataList( first, count );
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, StaffEntityComposite obj )
    {
        final String staffId = obj.identity().get();

        item.add( createDetailLink( "firstName", obj.getFirstName(), staffId ) );
        item.add( createDetailLink( "lastName", obj.getLastName(), staffId ) );

        Money salary = obj.getSalary();

        item.add( new Label( "salary", salary.getCurrency().getSymbol() + salary.getAmount() ) );

        item.add( new Label( "loginId", obj.getLogin().getName() ) );

        item.add( new SimpleCheckBox( "loginEnabled", obj.getLogin().getEnabled(), true ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                setResponsePage( new StaffEditPage( (BasePage) this.getPage() )
                {
                    public StaffEntityComposite getStaff()
                    {
                        return getStaffService().get( staffId );
                    }
                } );
            }
        } );
    }

    private SimpleLink createDetailLink( String id, String text, final String staffId )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
                StaffDetailPage detailPage = new StaffDetailPage( (BasePage) this.getPage() )
                {
                    public StaffEntityComposite getStaff()
                    {
                        return getStaffService().get( staffId );
                    }
                };

                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "First Name", "Last name", "Salary", "Login Id", "Login Enabled", "Edit" );
    }

    public abstract int getSize();

    public abstract List<StaffEntityComposite> dataList( int first, int count );
}
