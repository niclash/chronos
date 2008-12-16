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
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

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

    public enum Policy
    {
        DISACRD_ON_END_REQUEST, DO_NOTHING_ON_END_REQUEST, RESET_ON_END_REQUEST
    }

    private Policy policy;

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

    public void setPolicy( Policy policy )
    {
        this.policy = policy;
    }


    private void setUpNewUnitOfWork()
    {
        currentUnitOfWork = unitOfWorkFactory.newUnitOfWork();
        version++;
        policy = Policy.DO_NOTHING_ON_END_REQUEST;
    }

    private void attach()
    {
        if( currentUnitOfWork == null )
        {
            setUpNewUnitOfWork();
        }
        else
        {
            currentUnitOfWork.resume();
        }
    }

    private void detach()
    {
        if( policy == Policy.DISACRD_ON_END_REQUEST )
        {
            currentUnitOfWork.discard();
            currentUnitOfWork = null;
        }
        else if( policy == Policy.RESET_ON_END_REQUEST )
        {
            currentUnitOfWork.reset();
        }
        else
        {
            currentUnitOfWork.pause();
        }
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
     * Return the current version of {@link org.qi4j.api.unitofwork.UnitOfWork}. The current version of {@link UnitOfWork} may be used to
     * determine if an entity already detached. An entity is considered detached, when its
     * UnitOfWork already been closed when it needs to re-associate another UnitOfWork before it
     * can be accessed.
     *
     * {@see class ChronosEntityModel}
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
     * in {@link org.qi4j.chronos.ui.wicket.model.ChronosEntityModel } re-associate to newly created UnitOfWork.
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
     * in {@link org.qi4j.chronos.ui.wicket.model.ChronosEntityModel} re-associate to newly created UnitOfWork.
     */
    public void discardCurrentUnitOfWork()
    {
        currentUnitOfWork.discard();
        setUpNewUnitOfWork();
    }
}
