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
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextArea;
import org.qi4j.chronos.ui.common.SimpleTextField;

public abstract class LegalConditionDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public LegalConditionDetailPage( Page returnBack )
    {
        this.returnPage = returnBack;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new LegalConditionDetailForm( "legalConditionDetailForm" ) );
    }

    private class LegalConditionDetailForm extends Form
    {
        private SimpleTextField nameField;
        private SimpleTextArea descField;

        private Button submitButton;

        public LegalConditionDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            LegalConditionComposite legalCondition = getLegalCondition();

            nameField = new SimpleTextField( "nameField", legalCondition.name().get(), true );
            descField = new SimpleTextArea( "descTextArea", legalCondition.description().get(), true );

            submitButton = new Button( "submitButton", new Model( "Return" ) );

            add( nameField );
            add( descField );

            add( submitButton );
        }

        protected void delegateSubmit( IFormSubmittingComponent submittingButton )
        {
            if( submittingButton == submitButton )
            {
                setResponsePage( returnPage );
            }
            else
            {
                throw new IllegalArgumentException( submittingButton + " is not handled yet!" );
            }
        }
    }

    public abstract LegalConditionComposite getLegalCondition();
}
