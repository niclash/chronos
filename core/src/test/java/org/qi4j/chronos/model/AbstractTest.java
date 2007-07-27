package org.qi4j.chronos.model;

import junit.framework.TestCase;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.CompositeModelFactory;
import org.qi4j.api.IdentityGenerator;
import org.qi4j.runtime.CompositeBuilderFactoryImpl;
import org.qi4j.runtime.EntityRepositoryImpl;
import org.qi4j.runtime.CompositeModelFactoryImpl;
import org.qi4j.runtime.UuidIdentityGenerator;
import org.qi4j.spi.serialization.SerializablePersistenceSpi;
import org.qi4j.extension.persistence.quick.MapPersistenceProvider;
import org.qi4j.extension.persistence.quick.SerializablePersistence;

public abstract class AbstractTest extends TestCase
{
    protected EntityRepositoryImpl repository;
    protected CompositeBuilderFactory builderFactory;
    private SerializablePersistence storage;
    private CompositeModelFactory modelFactory;

    protected void setUp() throws Exception
    {
        modelFactory = new CompositeModelFactoryImpl();
        builderFactory = new CompositeBuilderFactoryImpl();
        IdentityGenerator identityGenerator = new UuidIdentityGenerator();
        repository = new EntityRepositoryImpl( builderFactory, identityGenerator );
        SerializablePersistenceSpi subsystem = new MapPersistenceProvider();
        storage = new SerializablePersistence( subsystem, modelFactory, builderFactory, repository );
        repository.setStorage( storage );
    }
}
