package org.qi4j.chronos.model;

import java.io.Serializable;

public interface Login extends Serializable
{
    String getLoginId();

    void setLoginId();

    String getPassword();

    void setPassword();
}
