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
package org.qi4j.chronos.service.lab;

import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.Account;
import org.qi4j.service.Activatable;

public class NTTServiceImpl extends NTTServiceAbstract<Account, AccountEntityComposite> implements NTTService<Account, AccountEntityComposite>, Activatable
{
    public NTTServiceImpl()
    {
        this.clazz = AccountEntityComposite.class;
    }
//    private SortedSet<String> cached = new TreeSet<String>();
//
//    public Account get( UnitOfWork uow, String id )
//    {
//        synchronized( cached )
//        {
//            if( cached.contains( id ) )
//            {
//                return uow.find( id, AccountEntityComposite.class );
//            }
//        }
//
//        return null;
//    }
//
//    public void save( UnitOfWork uow, Account account ) throws UnitOfWorkCompletionException
//    {
//        if( account != null )
//        {
//            saveAll( uow, Arrays.asList( account ) );
//        }
//        else
//        {
//            uow.complete();
//        }
//    }
//
//    public void saveAll( UnitOfWork uow, Collection<Account> accounts ) throws UnitOfWorkCompletionException
//    {
//        List<String> temp = new ArrayList<String>();
//        for( Account account : accounts )
//        {
//            temp.add( ((AccountEntityComposite) account).identity().get() );
//        }
//
//        uow.complete();
//
//        synchronized( cached )
//        {
//            cached.addAll( temp );
//        }
//    }
//
//    public void delete( UnitOfWork uow, String id ) throws UnitOfWorkCompletionException
//    {
//        synchronized( cached )
//        {
//            if( cached.contains( id ) )
//            {
//                delete( uow, get( uow, id ) );
//            }
//            else
//            {
//                uow.complete();
//            }
//        }
//    }
//
//    public void delete( UnitOfWork uow, Account account ) throws UnitOfWorkCompletionException
//    {
//        if( account != null )
//        {
//            deleteAll( uow, Arrays.asList( account ) );
//        }
//        else
//        {
//            uow.complete();
//        }
//    }
//
//    public void deleteAll( UnitOfWork uow, Collection<Account> accounts ) throws UnitOfWorkCompletionException
//    {
//        List<String> ids = new ArrayList<String>();
//
//        for( Account account : accounts )
//        {
//            ids.add( ((AccountEntityComposite) account).identity().get() );
//            uow.remove( account );
//        }
//        uow.complete();
//
//        synchronized( cached )
//        {
//            cached.removeAll( ids );
//        }
//    }
//
//    public List<Account> findAll( UnitOfWork uow )
//    {
//        List<Account> temp = new ArrayList<Account>();
//        for( String accountId : cached )
//        {
//            temp.add( uow.find( accountId, AccountEntityComposite.class ) );
//        }
//        return Collections.synchronizedList( temp );
//    }
//
//    public List<Account> find( UnitOfWork uow, FindFilter findFilter )
//    {
//        return Collections.synchronizedList( findAll( uow ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() ) );
//    }
//
//    public int countAll()
//    {
//        return cached.size();
//    }
//
//    public AccountEntityComposite newInstance( UnitOfWork uow, Class<AccountEntityComposite> clazz )
//    {
//        CompositeBuilder<AccountEntityComposite> builder = uow.newEntityBuilder( clazz );
//        return builder.newInstance();
//    }

    public void activate() throws Exception
    {
    }

    public void passivate() throws Exception
    {
    }
}
