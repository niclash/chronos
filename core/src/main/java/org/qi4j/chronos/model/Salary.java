package org.qi4j.chronos.model;

import org.qi4j.chronos.model.composites.MoneyComposite;

public interface Salary
{
    MoneyComposite getSalary();

    void setSalary(MoneyComposite salary);
}
