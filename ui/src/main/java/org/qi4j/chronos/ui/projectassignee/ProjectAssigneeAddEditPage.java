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
package org.qi4j.chronos.ui.projectassignee;

import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

public abstract class ProjectAssigneeAddEditPage extends AddEditBasePage
{
    private SimpleDropDownChoice projectRoleChoice;
    private SimpleDropDownChoice staffChoice;
    private SimpleDropDownChoice priceRateSchedule;

    public ProjectAssigneeAddEditPage( BasePage basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        //TODO
    }

    public void handleSubmit()
    {
        onsubmitting();
    }

    public abstract void onsubmitting();

    public abstract ProjectEntityComposite getProject();
}
