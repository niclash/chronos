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
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.Relationship;

public abstract class RelationshipAddPage extends RelationshipAddEditPage
{
    private static final long serialVersionUID = 1L;

    public RelationshipAddPage( Page basePage, IModel<Relationship> model )
    {
        super( basePage, model );
    }

    public void onSubmitting()
    {
        newRelationship( (Relationship) getModelObject() );
        divertToGoBackPage();
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Relationship";
    }

    public abstract Customer getCustomer();

    public abstract void newRelationship( Relationship relationship );
}
