package org.qi4j.chronos.service.authentication;

import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasLogin;

/**
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public interface AuthenticationService
{
    boolean authenticate( HasLogin hasLogin, String username, String password );

    User authenticate( Account anAccount, String aUsername, String aPassword );
}
