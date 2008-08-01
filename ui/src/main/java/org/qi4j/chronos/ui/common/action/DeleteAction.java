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
package org.qi4j.chronos.ui.common.action;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.entity.Identity;

public abstract class DeleteAction<T extends Identity> extends DefaultAction<T>
{
    private static final long serialVersionUID = 1L;

    private static final IModel<String> CONFIRM_MSG = new Model<String>( "Are you sure want to delete selected item(s)?" );

    public DeleteAction( String actionName )
    {
        super( actionName, true, CONFIRM_MSG );
    }
}
