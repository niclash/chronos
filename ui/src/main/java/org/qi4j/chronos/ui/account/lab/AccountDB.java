/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.ui.account.lab;

import org.qi4j.chronos.service.account.AccountService;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.chronos.model.Account;
import org.qi4j.entity.Identity;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class AccountDB
{
    private static final AccountDB db = new AccountDB();

    private Map<String, Account> map = Collections.synchronizedMap(new HashMap<String, Account>());
    private List<Account> nameAsc = Collections.synchronizedList( new ArrayList<Account>() );
    private List<Account> nameDesc = Collections.synchronizedList( new ArrayList<Account>() );
    private List<Account> referenceAsc = Collections.synchronizedList( new ArrayList<Account>() );
    private List<Account> referenceDesc = Collections.synchronizedList( new ArrayList<Account>() );

    private AccountService getAccountService()
    {
        return ChronosSession.get().getAccountService();
    }

    private AccountDB()
    {
        init();
    }

    private void init()
    {
        clean();
        for( Account account : getAccountService().findAll() )
        {
            addAccount( account );
        }
        updateIndecies();
    }

    private void clean()
    {
        map.clear();
        nameAsc.clear();
        nameDesc.clear();
        referenceAsc.clear();
        referenceDesc.clear();
    }

    public static final AccountDB getSingletonInstance()
    {
        return db;
    }

    private void updateIndecies()
    {
        Collections.sort( nameAsc, new AscendingNameComparator() );
        Collections.sort( nameDesc, new DescendingNameComparator() );
        Collections.sort( referenceAsc, new AscendingReferenceComparator() );
        Collections.sort( referenceDesc, new DescendingReferenceComparator() );
    }

    private void addAccount( Account account )
    {
        map.put( ( (Identity) account).identity().get(), account );
        nameAsc.add( account );
        nameDesc.add( account );
        referenceAsc.add( account );
        referenceDesc.add( account );
    }

    public Account get( String id )
    {
        init();
        return map.get( id );
    }

    public int getCount()
    {
        init();
        return nameAsc.size();
    }

    public List<Account> find( int first, int count, String sortProperty, boolean sortAsc )
    {
        init();
        return getIndex( sortProperty, sortAsc ).subList( first, first + count );
    }

    private List getIndex( String sortProperty, boolean sortAsc )
    {
        if( sortProperty == null )
        {
            return nameAsc;
        }
        else if( sortProperty.equals( "name" ) )
        {
            return ( sortAsc ) ? nameAsc : nameDesc;
        }
        else if( sortProperty.equals( "reference" ) )
        {
            return ( sortAsc ) ? referenceAsc : referenceDesc;
        }

        return Collections.EMPTY_LIST;
    }
}
