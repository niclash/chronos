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

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;

public abstract class ProjectDetailPage extends LeftMenuNavPage
{
    private BasePage returnPage;

    public ProjectDetailPage( BasePage returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ProjectDetailForm( "projectDetailForm" ) );
    }

    private class ProjectDetailForm extends Form
    {
        private Button submitButton;

        public ProjectDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            submitButton = new Button( "submitButton", new Model( "Return" ) );

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
                throw new IllegalArgumentException( submittingButton + " not handled yet." );
            }
        }
    }

    public abstract ProjectEntityComposite getProject();
}