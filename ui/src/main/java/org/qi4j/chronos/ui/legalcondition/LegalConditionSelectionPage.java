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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.service.LegalConditionService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.SimpleTextArea;

public abstract class LegalConditionSelectionPage extends LeftMenuNavPage
{
    private Page returnPage;

    public LegalConditionSelectionPage( Page returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new LegalConditionSelectionForm( "legalConditionSelectionForm" ) );
    }

    private class LegalConditionSelectionForm extends Form
    {
        private Button submitButton;
        private Button cancelButton;

        private SimpleDropDownChoice<LegalConditionDelegator> legalConditionChoice;

        private SimpleTextArea descTextArea;

        public LegalConditionSelectionForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            List<LegalConditionDelegator> list = getAvailableLegalCondition();

            legalConditionChoice = new SimpleDropDownChoice<LegalConditionDelegator>( "legalConditionChoice", list, true )
            {
                protected void onSelectionChanged( Object newSelection )
                {
                    handleSelectionChanged();
                }

                protected boolean wantOnSelectionChangedNotifications()
                {
                    return true;
                }
            };

            descTextArea = new SimpleTextArea( "descTextArea", legalConditionChoice.getChoice().toString() );

            submitButton = new Button( "submitButton", new Model( "Select" ) )
            {
                public void onSubmit()
                {
                    handleSelect();
                }
            };

            cancelButton = new Button( "cancelButton", new Model( "Cancel" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            add( legalConditionChoice );
            add( descTextArea );
            add( submitButton );
            add( cancelButton );
        }

        private void handleSelect()
        {
            LegalConditionComposite legalCondition = ChronosWebApp.newInstance( LegalConditionComposite.class );

            legalCondition.name().set( legalConditionChoice.getChoice().getName() );
            legalCondition.description().set( legalConditionChoice.getChoice().getDesc() );

            selectedLegalCondition( legalCondition );

            setResponsePage( returnPage );
        }

        private void handleSelectionChanged()
        {
            descTextArea.setText( legalConditionChoice.getChoice().getDesc() );
        }
    }

    private List<LegalConditionDelegator> getAvailableLegalCondition()
    {
        List<LegalConditionComposite> list = getLegalConditionService().findAll( getAccount() );

        List<LegalConditionDelegator> resultList = new ArrayList<LegalConditionDelegator>();

        for( LegalConditionComposite legalCondition : list )
        {
            resultList.add( new LegalConditionDelegator( legalCondition ) );
        }

        return resultList;
    }

    private LegalConditionService getLegalConditionService()
    {
        return ChronosWebApp.getServices().getLegalConditionService();
    }

    public abstract void selectedLegalCondition( LegalConditionComposite legalCondition );
}
