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

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.qi4j.ui.Qi4jUIRuntimeException;
import org.qi4j.ui.response.WebResponse;

public class WebResponseMixin implements WebResponse
{
    private HttpServletResponse response;

    public void setHttpServletResponse( HttpServletResponse response )
    {
        this.response = response;
    }

    public HttpServletResponse getHttpServletResponse()
    {
        return response;
    }

    public void setHeader( String header, String value )
    {
        response.setHeader( header, value );
    }

    public void write( String str )
    {
        try
        {
            response.getWriter().write( str );
        }
        catch( IOException err )
        {
            throw new Qi4jUIRuntimeException( err.getMessage(), err );
        }
    }

    public void write( CharSequence charSequence )
    {
        write( charSequence.toString() );
    }
}
