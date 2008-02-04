package org.qi4j.chronos.service.user;

import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.AccountEntityComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public abstract class UserServiceImpl
    implements UserService
{
    public User getUserById( String aUserId )
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public User getUser( AccountEntityComposite account, String loginId, String password )
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public User getUser( String loginId, String password )
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void update( User user )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasThisSystemRole( User user, String systemRoleName )
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
