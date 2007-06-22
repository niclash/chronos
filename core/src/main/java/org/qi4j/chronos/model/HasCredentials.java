package org.qi4j.chronos.model;

import java.util.Dictionary;
import java.util.List;
import java.util.Iterator;

public interface HasCredentials
{
    void addCredential( Dictionary dictionary);

    void removeCredential(Dictionary dictionary);

    Iterator<Dictionary> credentialIterator();
}
