package org.qi4j.chronos.model.composites;

import org.qi4j.api.Composite;
import org.qi4j.chronos.model.HasCredentials;
import org.qi4j.chronos.model.Login;

 public interface UserComposite extends PersonComposite, Login, HasCredentials, Composite
{
}
