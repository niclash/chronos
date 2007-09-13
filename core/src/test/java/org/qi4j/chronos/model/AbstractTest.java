package org.qi4j.chronos.model;

import junit.framework.TestCase;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.CompositeModelFactory;
import org.qi4j.api.persistence.EntitySession;
import org.qi4j.api.persistence.IdentityGenerator;
import org.qi4j.extension.persistence.quick.MapPersistenceProvider;
import org.qi4j.extension.persistence.quick.SerializablePersistence;
import org.qi4j.runtime.CompositeBuilderFactoryImpl;
import org.qi4j.runtime.CompositeModelFactoryImpl;
import org.qi4j.runtime.UuidIdentityGenerator;
import org.qi4j.runtime.persistence.EntitySessionImpl;
import org.qi4j.spi.serialization.SerializablePersistenceSpi;

public abstract class AbstractTest extends TestCase
{
    protected EntitySession session;
    protected CompositeBuilderFactory builderFactory;
    private SerializablePersistence storage;
    private CompositeModelFactory modelFactory;

    protected void setUp() throws Exception
    {
        modelFactory = new CompositeModelFactoryImpl();
        builderFactory = new CompositeBuilderFactoryImpl();
        IdentityGenerator identityGenerator = new UuidIdentityGenerator();
        SerializablePersistenceSpi subsystem = new MapPersistenceProvider();
        storage = new SerializablePersistence( subsystem, modelFactory, builderFactory, session );
        session = new EntitySessionImpl( storage, modelFactory, builderFactory, identityGenerator );
    }
}
