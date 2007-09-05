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
package org.qi4j.chronos.ui.projectowner;

import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.customer.CustomerAddEditPanel;

public abstract class ProjectOwnerAddEditPage extends AddEditBasePage
{
    private CustomerAddEditPanel customerAddEditPanel;

    public ProjectOwnerAddEditPage( BasePage basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        customerAddEditPanel = new CustomerAddEditPanel( "customerAddEditPanel" );

        form.add( customerAddEditPanel );
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( customerAddEditPanel.checkIsNotValidated() )
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
