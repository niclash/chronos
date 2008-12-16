package org.qi4j.chronos.ui.wicket.bootstrap;

import org.qi4j.api.service.ServiceFinder;

/**
 * @author Lan Boon Ping
 */
public final class ChronosServiceFinderHelper
{
    private static final ThreadLocal<ChronosServiceFinderHelper> current = new ThreadLocal<ChronosServiceFinderHelper>();

    private ServiceFinder serviceFinder;

    public ChronosServiceFinderHelper( ServiceFinder serviceFinder )
    {
        this.serviceFinder = serviceFinder;
    }

    public <P> P findService( Class<P> serviceType )
    {
        return serviceFinder.findService( serviceType ).get();
    }

    public static void set( ChronosServiceFinderHelper helper )
    {
        current.set( helper );
    }

    public static ChronosServiceFinderHelper get()
    {
        return current.get();
    }
}
