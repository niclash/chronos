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
package org.qi4j.chronos.ui.account.lab;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;

public class CompositePropertyColumn extends PropertyColumn
{
    private String propertyExpression;

    public CompositePropertyColumn( IModel displayModel, String sortProperty, String propertyExpression )
    {
        super( displayModel, sortProperty, propertyExpression );
        this.propertyExpression = propertyExpression;
    }

    public CompositePropertyColumn( IModel displayModel, String propertyExpression )
    {
        super( displayModel, propertyExpression );
        this.propertyExpression = propertyExpression;
    }

    @Override protected IModel createLabelModel( IModel embeddedModel )
    {
        return new CustomCompositeModel( embeddedModel, propertyExpression );
    }
}