/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.ui.address;

import org.apache.wicket.model.IModel;
import java.io.Serializable;

public class CompositeModel implements IModel
{
    private IModel iModel;
    private String propertyName;
    private static final String GET = "get";
    private static final String SET = "set";


    public CompositeModel( IModel iModel, String propertyName )
    {
        this.iModel = iModel;
        this.propertyName = propertyName;
    }

    public Object getObject()
    {
        try
        {
            Object object = iModel.getObject();
            Object temp = object.getClass().getMethod( propertyName ).invoke( object );

            return temp.getClass().getMethod( GET ).invoke( temp );
        }
        catch( Exception ex )
        {
            System.err.println( ex.getLocalizedMessage() );
        }

        return null;
    }

    public void setObject( Object object )
    {
        try
        {
            Object obj = iModel.getObject();
            Object temp = obj.getClass().getMethod( propertyName ).invoke( obj );

            temp.getClass().getMethod( SET, Object.class ).invoke( temp, object );
        }
        catch( Exception ex )
        {
            System.err.println( ex.getLocalizedMessage() );
        }
    }

    public void detach()
    {
        iModel.detach();
    }
}
