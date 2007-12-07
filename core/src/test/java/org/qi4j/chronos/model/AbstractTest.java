package org.qi4j.chronos.model;

import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.entity.EntitySession;
import org.qi4j.entity.IdentityGenerator;
import org.qi4j.extension.persistence.quick.MapPersistenceProvider;
import org.qi4j.extension.persistence.quick.SerializablePersistence;
import org.qi4j.runtime.composite.UuidIdentityGenerator;
import org.qi4j.runtime.entity.EntitySessionImpl;
import org.qi4j.spi.serialization.SerializablePersistenceSpi;
import org.qi4j.test.AbstractQi4jTest;

public abstract class AbstractTest extends AbstractQi4jTest
{
    protected EntitySession session;
    protected CompositeBuilderFactory builderFactory;

    protected void setUp() throws Exception
    {
        super.setUp();

        IdentityGenerator identityGenerator = new UuidIdentityGenerator();
        SerializablePersistenceSpi subsystem = new MapPersistenceProvider();
        SerializablePersistence storage = new SerializablePersistence( subsystem, spi );
        session = new EntitySessionImpl( storage, builderFactory, identityGenerator );
    }
}
