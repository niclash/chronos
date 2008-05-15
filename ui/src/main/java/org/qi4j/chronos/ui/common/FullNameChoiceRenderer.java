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
package org.qi4j.chronos.ui.common;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.qi4j.library.general.model.PersonName;
import org.qi4j.entity.Identity;

public class FullNameChoiceRenderer
    implements IChoiceRenderer
{
    private static final long serialVersionUID = 1L;

    public Object getDisplayValue( Object object )
    {
        PersonName personName = (PersonName) object;
        return personName.fullName().get();
    }

    public String getIdValue( Object object, int index )
    {
        Identity identity = (Identity) object;
        return identity.identity().get();
    }
}
