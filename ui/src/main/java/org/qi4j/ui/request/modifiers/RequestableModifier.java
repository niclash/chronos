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
package org.qi4j.ui.request.modifiers;

import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.ui.HasWebApplication;
import org.qi4j.ui.WebApplication;
import org.qi4j.ui.component.composites.PageComposite;
import org.qi4j.ui.request.Requestable;
import org.qi4j.ui.request.association.HasRequest;
import org.qi4j.ui.response.association.HasResponse;

public class RequestableModifier implements Requestable
{
    @Modifies private Requestable next;

    @Uses private HasWebApplication hasWebApplication;

    @Uses private HasRequest hasRequest;

    @Uses private HasResponse hasResponse;

    @Dependency private CompositeBuilderFactory factory;

    public void request()
    {
        WebApplication application = hasWebApplication.getWebApplication();

        String path = hasRequest.getRequest().getPath();

        Class clazz = application.getPage( path );

        if( clazz != null )
        {
            PageComposite composite = (PageComposite) factory.newCompositeBuilder( clazz ).newInstance();

            composite.init();

            composite.render( hasResponse.getResponse() );
        }
        else
        {
            hasResponse.getResponse().write( "Internal Error! Unable to resolve path " + path );
        }

        next.request();
    }
}
