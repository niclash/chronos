/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 8, 2008
 * Time: 10:45:24 PM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.service.account;

import org.qi4j.chronos.model.Account;
import org.qi4j.entity.UnitOfWork;
import java.util.List;
import java.util.Collection;

public interface AccountService
{
    String getId( Account account );

    Account get( String accountId );

    Account get( UnitOfWork unitOfWork, String accountId );

//    Account newInstance( UnitOfWork unitOfWork );

    void add( Account account );

    void remove( Account account );

    void removeAll( Collection<Account> accounts );

    List<Account> findAvailableAccounts();

    List<Account> findAll();

    List<Account> findAll( int first, int count );

    Account findAccountByName( String accountName );

    boolean isUnique( String accountName );

    int count();

    void enableAccounts( Collection<Account> accounts, boolean enabled );
}
