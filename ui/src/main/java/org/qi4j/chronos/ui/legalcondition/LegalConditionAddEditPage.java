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
package org.qi4j.chronos.ui.legalcondition;

import org.apache.wicket.Page;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;
import org.qi4j.chronos.ui.common.MaxLengthTextArea;
import org.qi4j.chronos.ui.common.MaxLengthTextField;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class LegalConditionAddEditPage extends AddEditBasePage
{
    private MaxLengthTextField nameField;
    private MaxLengthTextArea descField;

    private SubmitLink selectLegalConditionLink;
    private WebMarkupContainer selectLegalConditionContainer;

    public LegalConditionAddEditPage( Page goBackPage )
    {
        super( goBackPage );
    }

    protected void hideSelectionLegalConditionLink()
    {
        selectLegalConditionContainer.setVisible( false );
    }

    public void initComponent( Form form )
    {
        selectLegalConditionLink = new SubmitLink( "selectLegalConditionLink" )
        {
            public void onSubmit()
            {
                handleSelectLegalConditionClick();
            }
        };

        selectLegalConditionContainer = new WebMarkupContainer( "selectLegalConditionContainer" );
        selectLegalConditionContainer.add( selectLegalConditionLink );

        if( getServices().getLegalConditionService().countAll( getAccount() ) == 0 )
        {
            selectLegalConditionContainer.setVisible( false );
        }

        nameField = new MaxLengthTextField( "nameField", "Legal Condition Name",
                                            LegalCondition.NAME_LEN );
        descField = new MaxLengthTextArea( "descTextArea", "Legal Condition Description",
                                           LegalCondition.DESC_LEN );

        form.add( selectLegalConditionContainer );
        form.add( nameField );
        form.add( descField );
    }

    private void handleSelectLegalConditionClick()
    {
        LegalConditionSelectionPage page = new LegalConditionSelectionPage( this )
        {
            public void selectedLegalCondition( LegalConditionComposite legalCondition )
            {
                assignLegalConditionToFieldValue( legalCondition );
            }
        };

        setResponsePage( page );
    }

    protected void assignFieldValueToLegalCondition( LegalConditionComposite legalCondition )
    {
        legalCondition.name().set( nameField.getText() );
        legalCondition.description().set( descField.getText() );
    }

    protected void assignLegalConditionToFieldValue( LegalConditionComposite legalCondition )
    {
        nameField.setText( legalCondition.name().get() );
        descField.setText( legalCondition.description().get() );
    }

    public void handleSubmit()
    {
        boolean isRejected = false;

        if( nameField.checkIsEmptyOrInvalidLength() )
        {
            isRejected = true;
        }

        if( descField.checkIsEmptyOrInvalidLength() )
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
