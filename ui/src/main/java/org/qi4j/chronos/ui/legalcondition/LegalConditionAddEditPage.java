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
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.wicket.base.AddEditBasePage;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class LegalConditionAddEditPage extends AddEditBasePage<LegalCondition>
{
    private static final long serialVersionUID = 1L;

    private WebMarkupContainer selectLegalConditionContainer;

    public LegalConditionAddEditPage( Page goBackPage, IModel<LegalCondition> model )
    {
        super( goBackPage, model );
    }

    protected void hideSelectionLegalConditionLink()
    {
        selectLegalConditionContainer.setVisible( false );
    }

    public void initComponent( Form form )
    {
        SubmitLink selectLegalConditionLink = new SubmitLink( "selectLegalConditionLink" )
        {
            public void onSubmit()
            {
                handleSelectLegalConditionClick();
            }
        };

        selectLegalConditionContainer = new WebMarkupContainer( "selectLegalConditionContainer" );
        selectLegalConditionContainer.add( selectLegalConditionLink );

//        if( getServices().getLegalConditionService().countAll( getAccount() ) == 0 )
        // TODO kamil: need to understand this
        if( false )
        {
            selectLegalConditionContainer.setVisible( false );
        }

        TextField nameField = new TextField( "name" );
        TextField descField = new TextField( "description" );

        form.add( selectLegalConditionContainer );
        form.add( nameField );
        form.add( descField );
    }

    private void handleSelectLegalConditionClick()
    {
        LegalConditionSelectionPage page = new LegalConditionSelectionPage( this )
        {
            public void selectedLegalCondition( LegalCondition legalCondition )
            {
//                assignLegalConditionToFieldValue( legalCondition );
            }
        };

        setResponsePage( page );
    }

    public void handleSubmitClicked()
    {

        onSubmitting();
    }

    public abstract void onSubmitting();
}
