package org.qi4j.chronos.model;

import org.qi4j.library.general.model.Enabled;
import org.qi4j.library.general.model.Password;

/**
 * Generic interface for login storing login-name, password and enabled.
 */
public interface Login extends Name, Password, Enabled
{
    public final static int LOGIN_ID_LEN = 80;

    public final static int PASSWORD_LEN = 20;
}
