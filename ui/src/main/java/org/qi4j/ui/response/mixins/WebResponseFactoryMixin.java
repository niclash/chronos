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
package org.qi4j.ui.response.mixins;

import javax.servlet.http.HttpServletResponse;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.ui.response.Response;
import org.qi4j.ui.response.ResponseFactory;
import org.qi4j.ui.response.WebResponse;
import org.qi4j.ui.response.composites.WebResponseComposite;

public class WebResponseFactoryMixin implements ResponseFactory
{
    @Dependency private CompositeBuilderFactory factory;

    public Response newResponse( HttpServletResponse response )
    {
        WebResponse webRespons = factory.newCompositeBuilder( WebResponseComposite.class ).newInstance();

        webRespons.setHttpServletResponse( response );

        return webRespons;
    }
}
