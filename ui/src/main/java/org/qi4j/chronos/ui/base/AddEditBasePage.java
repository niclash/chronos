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
package org.qi4j.chronos.ui.base;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public abstract class AddEditBasePage extends LeftMenuNavPage
{
    private BasePage goBackPage;

    public AddEditBasePage( final BasePage goBackPage )
    {
        this.goBackPage = goBackPage;

        initComponents();

        final String titleLabel = getTitleLabel();
        add( new Label( "titleLabel", titleLabel ) );
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new AddEditForm( "addEditForm" ) );
    }

    private class AddEditForm extends Form
    {
        private Button submitButton;
        private Button cancelButton;

        public AddEditForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            initComponent( this );

            String buttonValue = getSubmitButtonValue();
            submitButton = new Button( "submitButton", new Model( buttonValue ) );
            cancelButton = new Button( "cancelButton", new Model( "Cancel" ) );

            add( submitButton );
            add( cancelButton );
        }

        public void delegateSubmit( IFormSubmittingComponent submittingComponent )
        {
            if( submittingComponent == submitButton )
            {
                handleSubmit();
            }
            else if( submittingComponent == cancelButton )
            {
                divertToGoBackPage();
            }
            else
            {
                throw new IllegalArgumentException( submittingComponent + " is not handled yet." );
            }
        }
    }

    protected void divertToGoBackPage()
    {
        setResponsePage( goBackPage );
    }

    protected void logMsg( String msg )
    {
        goBackPage.info( msg );
    }

    public abstract void initComponent( Form form );

    public abstract void handleSubmit();

    public abstract String getSubmitButtonValue();

    public abstract String getTitleLabel();
}

