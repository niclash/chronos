package org.qi4j.chronos.service.authentication;

import org.qi4j.service.ServiceComposite;

/**
 * @author edward.yakop@gmail.com
 * @since 0.1.0
 */
public interface AuthenticationService extends ServiceComposite
{

    boolean authenticate( String aUserName, String aPassword );
}
