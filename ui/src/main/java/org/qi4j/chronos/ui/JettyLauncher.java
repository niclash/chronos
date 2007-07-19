/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyLauncher
{

    public static void main( String[] args )
    {
        Server server = new Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort( 8080 );
        server.setConnectors( new Connector[]{ connector } );

        WebAppContext web = new WebAppContext();
        web.setContextPath( "/" );

        web.setWar( "../qi4j/apps/chronos/ui/src/webapp/" );
        server.addHandler( web );

        try
        {
            server.start();
            server.join();
        }
        catch( Exception e )
        {
            e.printStackTrace();
            System.exit( 100 );
        }
    }
}
