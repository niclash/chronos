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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.composites.LegalConditionEntityComposite;
import org.qi4j.chronos.service.LegalConditionService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.MaxLengthTextArea;
import org.qi4j.chronos.ui.common.MaxLengthTextField;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

public class GetLegalConditionPage extends LeftMenuNavPage
{
    private BasePage goBackPage;

    public GetLegalConditionPage( BasePage goBackPage )
    {
        this.goBackPage = goBackPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new GetOrNewLegalConditionForm( "getOrNewLegalConditionForm" ) );
    }

    private class LegalConditionDelegator implements Serializable
    {
        private String legalConditionName;
        private String legalConditionId;

        public LegalConditionDelegator( LegalConditionEntityComposite legalCondition )
        {
            this.legalConditionId = legalCondition.getIdentity();
            this.legalConditionName = legalCondition.getLegalConditionName();
        }

        public String getLegalConditionName()
        {
            return legalConditionName;
        }

        public String getLegalConditionId()
        {
            return legalConditionId;
        }

        public String toString()
        {
            return legalConditionName;
        }
    }

    private class GetOrNewLegalConditionForm extends Form
    {
        private MaxLengthTextField newNameField;
        private MaxLengthTextArea newDescField;

        private SimpleDropDownChoice existChoice;
        private Label existDescLabel;

        private CheckBox existCheckBox;
        private CheckBox newCheckBox;

        private CheckGroup checkBoxGroup;

        private Button submitButton;
        private Button cancelButton;

        public GetOrNewLegalConditionForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            newNameField = new MaxLengthTextField( "newNameField", "Legal Condition Name",
                                                   LegalCondition.LEGAL_CONDITION_NAME_LEN );
            newDescField = new MaxLengthTextArea( "newDescField", "Legal Condition Description",
                                                  LegalCondition.LEGAL_CONDITION_DESC_LEN );

            existChoice = new SimpleDropDownChoice( "existChoice", getLegalCondition(), true );
            existDescLabel = new Label( "existDescLabel", "" );

            existCheckBox = new CheckBox( "existCheckBox", new Model( "false" ) );
            newCheckBox = new CheckBox( "newCheckBox", new Model( "false" ) );

            checkBoxGroup = new CheckGroup( "checkBoxGroup" );

            checkBoxGroup.add( existCheckBox );
            checkBoxGroup.add( newCheckBox );

            submitButton = new Button( "submitButton", new Model( "Done" ) );
            cancelButton = new Button( "cancelButton", new Model( "Cancel" ) );

            add( newNameField );
            add( newDescField );

            add( existChoice );
            add( existDescLabel );

            add( checkBoxGroup );

            add( submitButton );
            add( cancelButton );
        }

        private List<LegalConditionDelegator> getLegalCondition()
        {
            LegalConditionService service = ChronosWebApp.getServices().getLegalConditionService();

            List<LegalConditionEntityComposite> list = service.findAll();

            List<LegalConditionDelegator> resultList = new ArrayList<LegalConditionDelegator>();

            for( LegalConditionEntityComposite legalCondition : list )
            {
                resultList.add( new LegalConditionDelegator( legalCondition ) );
            }

            return resultList;
        }

        protected void delegateSubmit( IFormSubmittingComponent submittingButton )
        {
            if( submittingButton == submitButton )
            {
                handleSubmit();
            }
            else
            {
                setResponsePage( goBackPage );
            }
        }

        private boolean isNewSelected()
        {
            return Boolean.parseBoolean( newCheckBox.getModelObjectAsString() );
        }

        private void handleSubmit()
        {
            boolean isRejected = false;

            if( isNewSelected() )
            {
                if( newNameField.checkIsEmptyOrInvalidLength() )
                {
                    isRejected = true;
                }

                if( newDescField.checkIsEmptyOrInvalidLength() )
                {
                    isRejected = true;
                }
            }

            if( isRejected )
            {
                return;
            }
        }
    }
}
