/*  Copyright 2008 Edward Yakop.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.wicket.bootstrap;

import org.apache.wicket.Response;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.qi4j.composite.scope.Structure;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.entity.UnitOfWorkFactory;

/**
 * {@code ChronosWebRequestCycle} responsible to:
 * <ul>
 * <li>
 * Manage unit of work. The algorithm is identical to open session in view.
 * <p/>
 * User is responsible to invoke {@link #completeUnitOfWork()} in order to complete the unit of work.
 * </li>
 * </ul>
 *
 * @author edward.yakop@gmail.com
 * @since 0.2.0
 */
public class ChronosWebRequestCycle extends WebRequestCycle
{
    @Structure
    private UnitOfWorkFactory uowf;

    private UnitOfWork currentUnitOfWork;

    private boolean isCompleteUnitOfWork;

    /**
     * Constructor which simply passes arguments to superclass for storage there. This instance will
     * be set as the current one for this thread.
     *
     * @param application The application.
     * @param request     The request.
     * @param response    The response.
     */
    public ChronosWebRequestCycle(
        @Uses WebApplication application,
        @Uses WebRequest request,
        @Uses Response response )
    {
        super( application, request, response );
    }

    /**
     * Gets request cycle for calling thread.
     *
     * @return Request cycle for calling thread
     */
    public static ChronosWebRequestCycle get()
    {
        return (ChronosWebRequestCycle) WebRequestCycle.get();
    }

    public final void completeUnitOfWork()
    {
        isCompleteUnitOfWork = true;
    }

    /**
     * Handles unit of work.
     *
     * @since 0.2.0
     */
    @Override
    protected final void onBeginRequest()
    {
        if( currentUnitOfWork == null )
        {
            currentUnitOfWork = uowf.newUnitOfWork();
            isCompleteUnitOfWork = false;
        }
        else
        {
            currentUnitOfWork.resume();
        }
    }

    /**
     * Handles unit of work.
     *
     * @since 0.2.0
     */
    @Override
    protected void onEndRequest()
    {
        if( isCompleteUnitOfWork )
        {
            try
            {
                currentUnitOfWork.complete();

            }
            catch( UnitOfWorkCompletionException e )
            {
                // By default discard it.
                currentUnitOfWork.discard();

                // TODO Log
                throw new WicketRuntimeException( e );
            }
            finally
            {
                currentUnitOfWork = null;
                isCompleteUnitOfWork = false;
            }
        }
        else
        {
            currentUnitOfWork.pause();
        }
    }
}
