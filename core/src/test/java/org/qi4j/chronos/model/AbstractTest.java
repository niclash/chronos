package org.qi4j.chronos.model;

import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.entity.EntitySession;
import org.qi4j.runtime.entity.EntitySessionInstance;
import org.qi4j.test.AbstractQi4jTest;

public abstract class AbstractTest extends AbstractQi4jTest
{
    protected EntitySession session;
    protected CompositeBuilderFactory builderFactory;

    protected void setUp() throws Exception
    {
        super.setUp();

        session = new EntitySessionInstance( moduleInstance );
    }
}
