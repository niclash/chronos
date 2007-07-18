/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.ui.model.mixins;

import org.qi4j.ui.model.Model;

public final class ModelMixin implements Model
{
    private Object model;

    public void setModel( Object object )
    {
        System.out.println( "Model null ? " + ( object == null ) );
        model = object;
    }

    public Object getModel()
    {
        return model;
    }

    public String toString()
    {
        if( model != null )
        {
            return model.toString();
        }
        else
        {
            return super.toString();
        }
    }
}
