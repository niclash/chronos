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
package org.qi4j.chronos.ui.contact;

import org.apache.wicket.Page;
import org.qi4j.chronos.model.composites.ContactComposite;

public abstract class ContactEditPage extends ContactAddEditPage
{
    public ContactEditPage( Page basePage )
    {
        super( basePage );

        initData();
    }

    private void initData()
    {
        ContactComposite contact = getContact();

        assignContactToFieldValue( contact );
    }

    public void onSubmitting()
    {
        //TODO fixme
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Contact";
    }

    public abstract ContactComposite getContact();
}
