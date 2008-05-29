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
package org.qi4j.chronos.ui.workentry;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;

public abstract class WorkEntryAddEditPage extends AddEditBasePage<WorkEntry>
{
    private static final long serialVersionUID = 1L;

    public WorkEntryAddEditPage( Page basePage, IModel<WorkEntry> workEntry )
    {
        super( basePage, workEntry );
    }

    public void initComponent( Form<WorkEntry> form )
    {
        RequiredTextField titleField = new RequiredTextField( "title" );
        TextArea descriptionTextArea = new TextArea( "description" );

        DateTimeField fromDateTimeField = new DateTimeField( "startTime" );
        DateTimeField toDateTimeField = new DateTimeField( "endTime" );

        form.add( titleField );
        form.add( descriptionTextArea );
        form.add( fromDateTimeField );
        form.add( toDateTimeField );
    }

    public void handleSubmitClicked( IModel<WorkEntry> model )
    {
        onSubmitting( model );
    }

    public abstract void onSubmitting( IModel<WorkEntry> model );
}
