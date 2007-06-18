package org.qi4j.chronos.model;

public interface LoginUser
{
    String getLoginId();

    String getPassword();

    boolean isEnabled();
}
