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

import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Money;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.associations.HasStaffs;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DefaultAction;
import org.qi4j.chronos.ui.common.action.DeleteAction;

public final class StaffTable extends ActionTable<Staff>
{
    private static final long serialVersionUID = 1L;

    private final static String[] COLUMN_NAMES = { "First Name", "Last name", "Salary", "Login Id", "Login Enabled", "Edit" };

    public StaffTable( String id, IModel<? extends HasStaffs> hasStaffs, StaffDataProvider dataProvider )
    {
        super( id, hasStaffs, dataProvider, COLUMN_NAMES );

        initActions();
    }

    private void initActions()
    {
        addAction( new DeleteAction<Staff>( "Delete Staff" )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Staff> staffs )
            {
//                getStaffService().delete( staffs );

                info( "Selected staff(s) are deleted." );
            }
        } );

        addAction( new DefaultAction<Staff>( "Disable Login" )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Staff> staffs )
            {
//                getStaffService().enableLogin( false, staffs );

                info( "Selected staff(s) are disabled login." );
            }
        } );

        addAction( new DefaultAction<Staff>( "Enable Login" )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<Staff> staffs )
            {
//                getStaffService().enableLogin( true, staffs );

                info( "Selected staff(s) are enabled login." );
            }
        } );
    }

    private SimpleLink createDetailLink( String id, String text, final String staffId )
    {
        return new SimpleLink( id, text )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
/*
                StaffDetailPage detailPage = new StaffDetailPage( this.getPage(), staffId );

                setResponsePage( detailPage );
*/
            }
        };
    }

    public void populateItems( Item<Staff> item )
    {
        final Staff staff = item.getModelObject();
        final String staffId = staff.identity().get();

        item.add( createDetailLink( "firstName", staff.firstName().get(), staffId ) );
        item.add( createDetailLink( "lastName", staff.lastName().get(), staffId ) );

        Money salary = staff.salary().get();
        item.add( new Label( "salary", salary.displayValue().get() ) );
        item.add( new Label( "loginId", staff.login().get().name().get() ) );
        item.add( new CheckBox( "loginEnabled", new Model<Boolean>( staff.login().get().isEnabled().get() ) ) );
        item.add( new SimpleLink( "editLink", "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                //TODO
//                    setResponsePage(
//                        new StaffEditPage( (BasePage) this.getPage(), staffId )
//                        {
//                            public Staff getStaff()
//                            {
//                                return staff;
//                            }
//                        }
//                    );
            }
        }
        );
    }
}
