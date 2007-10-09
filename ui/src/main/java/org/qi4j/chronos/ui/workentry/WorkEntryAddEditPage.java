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
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextArea;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDateTimeField;

public abstract class WorkEntryAddEditPage extends AddEditBasePage
{
    private MaxLengthTextField titleField;
    private MaxLengthTextArea descriptionTextArea;

    private SimpleDateTimeField fromDateTimeField;
    private SimpleDateTimeField toDateTimeField;

    public WorkEntryAddEditPage( Page basePage )
    {
        super( basePage );
    }

    public void initComponent( Form form )
    {
        titleField = new MaxLengthTextField( "titleField", "Title", WorkEntry.TITLE_LEN );
        descriptionTextArea = new MaxLengthTextArea( "descriptionTextArea", "Description", WorkEntry.DESCRIPTION_LEN );

        fromDateTimeField = new SimpleDateTimeField( "fromDateTimeField" );
        toDateTimeField = new SimpleDateTimeField( "toDateTimeField" );

        form.add( titleField );
        form.add( descriptionTextArea );
        form.add( fromDateTimeField );
        form.add( toDateTimeField );
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( titleField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( descriptionTextArea.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    protected void assignFieldValueToWorkEntry( WorkEntry workEntry )
    {
        workEntry.setTitle( titleField.getText() );
        workEntry.setDescription( descriptionTextArea.getText() );
        workEntry.setStartTime( fromDateTimeField.getDate() );
        workEntry.setEndTime( toDateTimeField.getDate() );
    }

    protected void assignWorkEntryToFieldValue( WorkEntry workEntry )
    {
        titleField.setText( workEntry.getTitle() );
        descriptionTextArea.setText( workEntry.getDescription() );
        fromDateTimeField.setDate( workEntry.getStartTime() );
        toDateTimeField.setDate( workEntry.getEndTime() );
    }

    public abstract void onSubmitting();
}
