package org.qi4j.chronos.model;

import java.util.Dictionary;
import java.util.List;

public interface HasCredentials
{
    void addCredential( Dictionary dictionary);

    void removeCredential(Dictionary dictionary);

    List<Dictionary> getCredentials();
}
