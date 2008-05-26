/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.wicket.bootstrap;

import java.io.Serializable;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.UnitOfWorkFactory;

/**
 * @author Lan Boon Ping
 */
public class ChronosUnitOfWorkManager
    implements Serializable
{
    private static final ThreadLocal<ChronosUnitOfWorkManager> current = new ThreadLocal<ChronosUnitOfWorkManager>();

    private static final long serialVersionUID = 1L;

    private UnitOfWorkFactory unitOfWorkFactory;

    private UnitOfWork currentUnitOfWork;

    private long version;

    static void set( ChronosUnitOfWorkManager unitOfWorkManager )
    {
        ChronosUnitOfWorkManager prevUnitOfWorkManager = current.get();

        if( prevUnitOfWorkManager != null )
        {
            prevUnitOfWorkManager.detach();
        }

        if( unitOfWorkManager != null )
        {
            unitOfWorkManager.attach();
        }
        current.set( unitOfWorkManager );
    }

    public static ChronosUnitOfWorkManager get()
    {
        return current.get();
    }

    public ChronosUnitOfWorkManager( UnitOfWorkFactory aUnitOfWorkFactory )
    {
        unitOfWorkFactory = aUnitOfWorkFactory;
        version = 0;
        setUpNewUnitOfWork();
    }

    private void setUpNewUnitOfWork()
    {
        currentUnitOfWork = unitOfWorkFactory.newUnitOfWork();
        version++;
    }

    private void attach()
    {
        currentUnitOfWork.resume();
    }

    private void detach()
    {
        currentUnitOfWork.pause();
    }

    /**
     * Return the current UnitOfWork.
     *
     * @return the current UnitOfWork
     */
    public UnitOfWork getCurrentUnitOfWork()
    {
        return currentUnitOfWork;
    }

    /**
     * Return the current version of {@link UnitOfWork}. The current version of {@link UnitOfWork} may be used to
     * determine if an entity already detached. An entity is considered detached, when its
     * UnitOfWork already been closed when it needs to re-associate another UnitOfWork before it
     * can be accessed.
     *
     * {@see class ChronosDetachableModel}
     *
     * @return the current version.
     */
    public long getVersion()
    {
        return version;
    }

    /**
     * Complete current UnitOfWork.
     *
     * If there is an {@link UnitOfWorkCompletionException} being thrown, you are required to
     * use {@link #discardCurrentUnitOfWork() method, instead of invoking unitOfWork.complete() directly.
     *
     * A new UnitOfWork will be created automatically on complete to enable entities hold
     * in {@link org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel } re-associate to newly created UnitOfWork.
     *
     * @throws UnitOfWorkCompletionException
     */
    public void completeCurrentUnitOfWork()
        throws UnitOfWorkCompletionException
    {
        currentUnitOfWork.complete();
        setUpNewUnitOfWork();
    }

    /**
     * Discard current UnitOfWork.
     *
     * A new UnitOfWork will be created automatically on discard to enable entities hold
     * in {@link org.qi4j.chronos.ui.wicket.model.ChronosDetachableModel} re-associate to newly created UnitOfWork.
     */
    public void discardCurrentUnitOfWork()
    {
        currentUnitOfWork.discard();
        setUpNewUnitOfWork();
    }
}
