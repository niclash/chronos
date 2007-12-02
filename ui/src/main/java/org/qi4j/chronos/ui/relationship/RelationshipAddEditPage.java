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
package org.qi4j.chronos.ui.relationship;

import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextField;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class RelationshipAddEditPage extends AddEditBasePage
{
    protected MaxLengthTextField relationshipField;

    public RelationshipAddEditPage( Page goBackPage )
    {
        super( goBackPage );
    }

    public void initComponent( Form form )
    {
        relationshipField = new MaxLengthTextField( "relationshipField", "Relationship",
                                                    Relationship.RELATIONSHIP_LEN );
        form.add( relationshipField );
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( relationshipField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    public abstract void onSubmitting();
}
