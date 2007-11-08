package org.qi4j.chronos.model;

import org.qi4j.CompositeBuilderFactory;
import org.qi4j.extension.persistence.quick.MapPersistenceProvider;
import org.qi4j.extension.persistence.quick.SerializablePersistence;
import org.qi4j.persistence.EntitySession;
import org.qi4j.persistence.IdentityGenerator;
import org.qi4j.runtime.CompositeModelFactory;
import org.qi4j.runtime.UuidIdentityGenerator;
import org.qi4j.runtime.persistence.EntitySessionImpl;
import org.qi4j.spi.serialization.SerializablePersistenceSpi;
import org.qi4j.test.AbstractQi4jTest;

public abstract class AbstractTest extends AbstractQi4jTest
{
    protected EntitySession session;
    protected CompositeBuilderFactory builderFactory;

    protected void setUp() throws Exception
    {
        super.setUp();

        CompositeModelFactory modelFactory = new CompositeModelFactory();
        IdentityGenerator identityGenerator = new UuidIdentityGenerator();
        SerializablePersistenceSpi subsystem = new MapPersistenceProvider();
        SerializablePersistence storage = new SerializablePersistence( subsystem, modelFactory, builderFactory );
        session = new EntitySessionImpl( storage, builderFactory, identityGenerator );
    }
}
