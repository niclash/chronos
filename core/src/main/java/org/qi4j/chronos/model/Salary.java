package org.qi4j.chronos.model;

import java.io.Serializable;
import org.qi4j.library.general.model.Money;

public interface Salary extends Serializable
{
    Money getSalary();

    void setSalary(Money salary);
}
