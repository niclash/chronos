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


    public UnitOfWork getCurrentUnitOfWork()
    {
        return currentUnitOfWork;
    }

    public long getVersion()
    {
        return version;
    }

    public void completeCurrentUnitOfWork()
        throws UnitOfWorkCompletionException
    {
        currentUnitOfWork.complete();
        setUpNewUnitOfWork();
    }

    public void discardCurrentUnitOfWork()
    {
        currentUnitOfWork.discard();
        setUpNewUnitOfWork();
    }
}
