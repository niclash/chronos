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

public interface AccountService
{
    String getId( Account account );
    
    Account get( UnitOfWork unitOfWork, String accountId );

    void add( Account account );

    void remove( Account account );

    List<Account> findAll();

    Account findAccountByName( String accountName );

    boolean isUnique( String accountName );

    int count();
}
