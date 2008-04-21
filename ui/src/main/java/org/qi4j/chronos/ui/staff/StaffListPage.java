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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public class StaffListPage extends LeftMenuNavPage
{
    public StaffListPage()
    {
        initComponents();
    }

    private void initComponents()
    {
        add( new Link( "newStaffLink" )
        {
            public void onClick()
            {
                setResponsePage( new StaffAddPage( StaffListPage.this ) );
            }
        } );

        add( new FeedbackPanel( "feedbackPanel" ) );

        StaffTable staffTable = new StaffTable( "staffTable" )
        {
            public int getSize()
            {
                return StaffListPage.this.getSize();
            }

            public List<Staff> dataList( int first, int count )
            {
                return StaffListPage.this.dataList( first, count );
            }
        };

        add( staffTable );
    }

    protected StaffService getStaffService()
    {
        return ChronosWebApp.getServices().getStaffService();
    }

    public int getSize()
    {
        // TODO migrate
//        return getStaffService().countAll( getAccount() );
        return getAccount().staffs().size();
    }

    public List<Staff> dataList( int first, int count )
    {
        return new ArrayList<Staff>( getAccount().staffs() );
//        Staff[] staffs = new Staff[ getSize() ];
//        return Arrays.asList( getAccount().staffs().toArray( staffs ) );
//        return new ArrayList<Staff>(0);
        // TODO
//        return getStaffService().findAll( getAccount(), new FindFilter( first, count ) );
    }
}
