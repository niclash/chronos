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
package org.qi4j.chronos.ui.account;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionAdapter;
import org.qi4j.chronos.ui.common.action.ActionTable;

public class AccountTable extends ActionTable<AccountEntityComposite>
{
    private SimpleDataProvider<AccountEntityComposite> dataProvider;

    public AccountTable( String id )
    {
        super( id );

        initAction();
    }

    private void initAction()
    {
        addAction( new ActionAdapter( "Delete" )
        {
            public void performAction( AbstractSortableDataProvider dataProvider )
            {
                info( "Delete - Not implemented yet - total items " + dataProvider.size() );
            }
        } );

        addAction( new ActionAdapter( "Disable" )
        {
            public void performAction( AbstractSortableDataProvider dataProvider )
            {
                info( "Disable - Not implemented yet - total items " + dataProvider.size() );
            }
        } );
    }

    public AbstractSortableDataProvider<AccountEntityComposite> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new SimpleDataProvider<AccountEntityComposite>()
            {
                public EntityService<AccountEntityComposite> getEntityService()
                {
                    return ChronosWebApp.getServices().getAccountService();
                }
            };
        }
        return dataProvider;
    }

    public void populateItems( Item item, AccountEntityComposite obj )
    {
        final String accountId = obj.getIdentity();

        item.add( new SimpleLink( "accountName", obj.getName() )
        {
            public void linkClicked()
            {
                setResponsePage( new AccountDetailPage( (BasePage) this.getPage(), accountId ) );
            }
        } );

        //TODO bp. fix these valeues.
        item.add( new Label( "totalProject", "10" ) );
        item.add( new Label( "activeProject", "3" ) );
        item.add( new Label( "inactiveProject", "2" ) );
        item.add( new Label( "closedProject", "5" ) );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Total Project", "Active", "Inactive", "Closed" );
    }
}
