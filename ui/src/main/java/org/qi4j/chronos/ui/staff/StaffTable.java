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
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.Money;
import org.qi4j.entity.Identity;

public abstract class StaffTable extends ActionTable<Staff, String>
{
    private StaffDataProvider dataProvider;

    public StaffTable( String id )
    {
        super( id );

        initActions();
    }

    private void initActions()
    {
        addAction( new SimpleDeleteAction<Staff>( "Delete Staff" )
        {
            public void performAction( List<Staff> staffs )
            {
                getStaffService().delete( staffs );

                info( "Selected staff(s) are deleted." );
            }
        } );

        addAction( new SimpleAction<Staff>( "Disable Login" )
        {
            public void performAction( List<Staff> staffs )
            {
                getStaffService().enableLogin( false, staffs );

                info( "Selected staff(s) are disabled login." );
            }
        } );

        addAction( new SimpleAction<Staff>( "Enable Login" )
        {
            public void performAction( List<Staff> staffs )
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

    public AbstractSortableDataProvider<Staff, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new StaffDataProvider()
            {
                public int getSize()
                {
                    return StaffTable.this.getSize();
                }

                public List<Staff> dataList( int first, int count )
                {
                    return StaffTable.this.dataList( first, count );
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, final Staff staff )
    {
        final String staffId = staff.identity().get();

        item.add( createDetailLink( "firstName", staff.firstName().get(), staffId ) );
        item.add( createDetailLink( "lastName", staff.lastName().get(), staffId ) );

        Money salary = staff.salary().get();

        item.add( new Label( "salary", salary.currency().get().getSymbol() + salary.amount().get() ) );

        item.add( new Label( "loginId", staff.login().get().name().get() ) );

        item.add( new SimpleCheckBox( "loginEnabled", staff.login().get().isEnabled().get(), true ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                setResponsePage(
                    new StaffEditPage( (BasePage) this.getPage(), staffId )
                    {
                        public Staff getStaff()
                        {
                            for( Staff staff : getAccount().staffs() )
                            {
                                if( staffId.equals( staff.identity().get() ) )
                                {
                                    return staff;
                                }
                            }

                            return null;
//                        return staff;
                            // TODO migrate
//                        return getStaffService().get( staffId );
                        }
                    }
                );
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
                    public Staff getStaff()
                    {
                        for( Staff staff : getAccount().staffs() )
                        {
                            if( staffId.equals( staff.identity().get() ) )
                            {
                                return staff;
                            }
                        }

                        return null;
                        // TODO kamil
//                        return getStaffService().get( staffId );
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

    public abstract List<Staff> dataList( int first, int count );
}
