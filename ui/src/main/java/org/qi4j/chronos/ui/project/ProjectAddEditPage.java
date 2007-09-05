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
package org.qi4j.chronos.ui.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.form.Form;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.ui.base.AddEditBasePage;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextArea;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDateField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.util.ListUtil;

public abstract class ProjectAddEditPage extends AddEditBasePage
{
    protected MaxLengthTextField projectNameField;
    protected MaxLengthTextArea formalReferenceField;

    protected SimpleDropDownChoice statusChoice;
    protected SimpleDropDownChoice primaryContactChoice;

    protected Palette contactPalette;

    protected SimpleDateField estimateStartDate;
    protected SimpleDateField estimateEndDate;

    protected SimpleDateField actualStartDate;
    protected SimpleDateField actualEndDate;

    protected SimpleDropDownChoice assigneeLeadChoice;
    protected Palette assigneePalette;

    private String accountId;

    public ProjectAddEditPage( BasePage basePage, String accountId )
    {
        super( basePage );

        this.accountId = accountId;
    }

    public void initComponent( Form form )
    {
        projectNameField = new MaxLengthTextField( "projectName", "Project Name", Project.PROJECT_NAME_LEN );
        formalReferenceField = new MaxLengthTextArea( "projectFormalReference", "Project Formal Reference", Project.PROJECT_FORMAL_REFERENCE_LEN );

        statusChoice = new SimpleDropDownChoice( "statusChoice", ListUtil.getProjectStatusList(), true );

        primaryContactChoice = new SimpleDropDownChoice( "primaryContactChoice", getContactPersonList(), true );
    }

    private List<ContactPersonDelegator> getContactPersonList()
    {
        List<ContactPersonDelegator> delegatorList = new ArrayList<ContactPersonDelegator>();

        //TODO bp. fixme
//        while( iterator.hasNext() )
//        {
//            delegatorList.add( new ContactPersonDelegator( iterator.next() ) );
//        }

        return delegatorList;
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( isRejected )
        {
            return;
        }

        onSubmitting();
    }

    private class ContactPersonDelegator implements Serializable
    {
        private String fullName;
        private String id;

        public ContactPersonDelegator( ContactPersonEntityComposite contactPerson )
        {
            this.fullName = contactPerson.getFirstName() + " " + contactPerson.getLastName();
            this.id = contactPerson.getIdentity();
        }

        public String toString()
        {
            return fullName;
        }
    }

    public abstract void onSubmitting();

}
