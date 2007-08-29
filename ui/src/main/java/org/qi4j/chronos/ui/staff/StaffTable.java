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
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.library.general.model.Money;

public class StaffTable extends ActionTable<StaffEntityComposite>
{
    private StaffDataProvider staffDataProvider;

    public StaffTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<StaffEntityComposite> getDetachableDataProvider()
    {
        if( staffDataProvider == null )
        {
            staffDataProvider = new StaffDataProvider();
        }

        return staffDataProvider;
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

        if( salary != null )
        {
            item.add( new Label( "salary", salary.getCurrency().getSymbol() + salary.getAmount() ) );
        }
        else
        {
            item.add( new Label( "salary", "--" ) );
        }

        item.add( new Label( "loginId", obj.getLogin().getName() ) );

        CheckBox loginEnabled = new CheckBox( "loginEnabled", new Model( obj.getLogin().isEnabled() ) );

        loginEnabled.setEnabled( false );
        item.add( loginEnabled );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "First Name", "Last name", "Salary", "Login Id", "Login Enabled" );
    }
}
