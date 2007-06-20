package org.qi4j.chronos.model.composites;

import org.qi4j.chronos.model.HasCredentials;
import org.qi4j.chronos.model.HasLogin;

public interface UserComposite extends PersonComposite, HasLogin, HasCredentials
{
}
