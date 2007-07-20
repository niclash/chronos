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
package org.qi4j.ui.request.mixins;

import org.qi4j.api.CompositeFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.ui.request.Request;
import org.qi4j.ui.request.RequestProcessorFactory;
import org.qi4j.ui.request.RequestProcessor;
import org.qi4j.ui.request.composites.RequestProcessorComposite;
import org.qi4j.ui.response.Response;

public class RequestProcessorFactoryMixin implements RequestProcessorFactory
{
    @Dependency private CompositeFactory factory;

    public RequestProcessor newRequestProcessor( Request request, Response response )
    {
        RequestProcessor processor = factory.newInstance( RequestProcessorComposite.class );

        processor.setRequest( request );
        processor.setResponse( response );

        return processor;
    }
}
