package org.qi4j.chronos.service.user;

import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.composite.Composite;
import org.qi4j.composite.Mixins;

/**
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
@Mixins( UserServiceImpl.class )
public interface UserService extends Composite
{
    User getUserById( String aUserId );

    User getUser( AccountEntityComposite account, String loginId, String password );

    //TODO bp. renamed this to getAdmin
    User getUser( String loginId, String password );

    void update( User user );

    boolean hasThisSystemRole( User user, String systemRoleName );
}
