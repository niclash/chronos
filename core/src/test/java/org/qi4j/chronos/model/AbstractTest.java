package org.qi4j.chronos.model;

import junit.framework.TestCase;
import org.qi4j.api.CompositeFactory;
import org.qi4j.runtime.CompositeFactoryImpl;

public abstract class AbstractTest extends TestCase
{
    protected CompositeFactory factory;

    protected void setUp() throws Exception
    {
        factory = new CompositeFactoryImpl();
    }
}
