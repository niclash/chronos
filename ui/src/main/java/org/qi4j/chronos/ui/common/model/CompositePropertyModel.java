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
package org.qi4j.chronos.ui.common.model;

import org.apache.wicket.model.PropertyModel;

public class CompositePropertyModel extends PropertyModel
{
    private static final String GET = "get";
    private static final String SET = "set";
    private Object modelObject;
    private String expression;

    /**
     * Construct with a wrapped (IModel) or unwrapped (non-IModel) object and a property expression
     * that works on the given model.
     *
     * @param modelObject The model object, which may or may not implement IModel
     * @param expression  Property expression for property access
     */
    public CompositePropertyModel( Object modelObject, String expression )
    {
        super( modelObject, expression );

        this.modelObject = modelObject;
        this.expression = expression;
    }

    @Override public Object getObject()
    {
        try
        {
            Object temp = modelObject.getClass().getMethod( expression ).invoke( modelObject );

            return temp.getClass().getMethod( GET ).invoke( temp );
        }
        catch( Exception ex )
        {
            System.err.println( ex.getLocalizedMessage() );
        }

        return null;
    }

    @Override public void setObject( Object object )
    {
        try
        {
            Object temp = modelObject.getClass().getMethod( expression ).invoke( modelObject );

            temp.getClass().getMethod( SET, Object.class ).invoke( temp, object );
        }
        catch( Exception ex )
        {
            System.err.println( ex.getLocalizedMessage() );
        }
    }
}
