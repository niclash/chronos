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
import org.qi4j.chronos.service.AccountService;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleCheckBox;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleAction;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;

public class AccountTable extends ActionTable<AccountEntityComposite, String>
{
    private SimpleDataProvider<AccountEntityComposite> dataProvider;

    public AccountTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<AccountEntityComposite>( "Delete account" )
        {
            public void performAction( List<AccountEntityComposite> accounts )
            {
                getAccountService().delete( accounts );

                info( "Selected account(s) are deleted." );
            }
        } );

        addAction( new SimpleAction<AccountEntityComposite>( "Disable account" )
        {
            public void performAction( List<AccountEntityComposite> accounts )
            {
                getAccountService().enableAccount( false, accounts );

                info( "Selected account(s) are disabled." );
            }
        } );

        addAction( new SimpleAction<AccountEntityComposite>( "Enable account" )
        {
            public void performAction( List<AccountEntityComposite> accounts )
            {
                getAccountService().enableAccount( true, accounts );

                info( "Selected account(s) are enabled." );
            }
        } );
    }

    public AbstractSortableDataProvider<AccountEntityComposite, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new SimpleDataProvider<AccountEntityComposite>()
            {
                public EntityService<AccountEntityComposite> getEntityService()
                {
                    return AccountTable.this.getAccountService();
                }
            };
        }
        return dataProvider;
    }

    private AccountService getAccountService()
    {
        return ChronosWebApp.getServices().getAccountService();
    }

    public void populateItems( Item item, AccountEntityComposite obj )
    {
        final String accountId = obj.getIdentity();

        item.add( createDetailPage( "name", obj.getName(), accountId ) );
        item.add( createDetailPage( "formalReference", obj.getReference(), accountId ) );

        item.add( new SimpleCheckBox( "enabled", obj.getEnabled(), true ) );

        //TODO bp. fix these valeues.
        item.add( new Label( "totalProject", "10" ) );
        item.add( new Label( "activeProject", "3" ) );
        item.add( new Label( "inactiveProject", "2" ) );
        item.add( new Label( "closedProject", "5" ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                AccountEditPage editPage = new AccountEditPage( this.getPage(), accountId );

                setResponsePage( editPage );
            }
        } );
    }

    private SimpleLink createDetailPage( String id, String displayValue, final String accountId )
    {
        return new SimpleLink( id, displayValue )
        {
            public void linkClicked()
            {
                AccountDetailPage detailPage = new AccountDetailPage( this.getPage(), accountId );

                setResponsePage( detailPage );
            }
        };
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "Formal Reference", "Enabled", "Total Project", "Active", "Inactive", "Closed", "" );
    }
}
