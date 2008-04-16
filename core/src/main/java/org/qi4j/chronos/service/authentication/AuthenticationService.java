package org.qi4j.chronos.service.authentication;

import org.qi4j.service.ServiceComposite;
import org.qi4j.service.Activatable;
import org.qi4j.composite.Mixins;
import org.qi4j.chronos.service.authentication.mixins.AuthenticationMixin;
import org.qi4j.chronos.model.associations.HasLogin;

/**
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public interface AuthenticationService
{
    boolean authenticate( HasLogin hasLogin, String username, String password );
}
