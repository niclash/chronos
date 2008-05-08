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

import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Name;

public class NameModel implements IModel
{
    private IModel iModel;

    public NameModel( IModel iModel )
    {
        this.iModel = iModel;
    }

    public Object getObject()
    {
        Name name = (Name) iModel.getObject();
        return name.name().get();
    }

    public void setObject( Object object )
    {
        Name name = (Name) iModel.getObject();
        name.name().set( (String) object );
    }

    public void detach()
    {
        iModel.detach();
    }
}
