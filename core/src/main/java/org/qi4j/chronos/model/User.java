package org.qi4j.chronos.model;

import org.qi4j.library.general.model.Person;
import org.qi4j.chronos.model.composites.association.HasCredentials;


public interface User extends Person, Login, HasCredentials
{
}
