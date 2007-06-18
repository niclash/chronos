package org.qi4j.chronos.model;

import java.util.Dictionary;

public interface User extends SystemRole
{
    String getFirstName();

    String getLastName();

    Gender getGender();

    LoginUser getLoginUser();

    Dictionary<String, Object> getCredentials();

    void addCredential(String key, Object value);

    void removeCredential(String key);
}
