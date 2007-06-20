package org.qi4j.chronos.model;

import java.util.Dictionary;

/**
 * TODO : Convert to COP
 */
public interface SystemRole
{
    String getName();

    int getType();

    Dictionary<String, String> getProperties();
}
