package org.qi4j.chronos.model;

import java.io.Serializable;
import org.qi4j.chronos.model.composites.MoneyComposite;

public interface Salary extends Serializable
{
    MoneyComposite getSalary();

    void setSalary(MoneyComposite salary);
}
