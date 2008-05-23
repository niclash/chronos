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
package org.qi4j.chronos.ui.wicket.base;

import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.library.framework.validation.ValidationMessage;

public abstract class AddEditBasePage extends LeftMenuNavPage
{
    private Page goBackPage;

    public AddEditBasePage( final Page goBackPage )
    {
        this.goBackPage = goBackPage;

        initComponents();

        final String titleLabel = getTitleLabel();
        add( new Label( "titleLabel", titleLabel ) );
    }

    public AddEditBasePage( final Page goBackPage, IModel iModel )
    {
        setModel( iModel );
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
        public AddEditForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            initComponent( this );

            String buttonValue = getSubmitButtonValue();
            final Button submitButton =
                new Button( "submitButton", new Model( buttonValue ) )
                {
                    public void onSubmit()
                    {
                        handleSubmit();
                    }
                };
            final Button cancelButton =
                new Button( "cancelButton", new Model( "Cancel" ) )
                {
                    public void onSubmit()
                    {
                        cancelToGoBackPage();
                    }
                };

            add( submitButton );
            add( cancelButton );
        }
    }

    protected void cancelToGoBackPage()
    {
//        reset();

        divertToGoBackPage();
    }

    protected void divertToGoBackPage()
    {
        setResponsePage( goBackPage );
    }

    protected Page getGoBackPage()
    {
        return goBackPage;
    }

    protected void logInfoMsg( String msg )
    {
        goBackPage.info( msg );
    }

    protected void logErrorMsg( String msg )
    {
        goBackPage.error( msg );
    }

    protected void logErrorMsg( List<ValidationMessage> msgs )
    {
        for( ValidationMessage msg : msgs )
        {
            logErrorMsg( msg.getMessage() );
        }
    }

    public abstract void initComponent( Form form );

    public abstract void handleSubmit();

    public abstract String getSubmitButtonValue();

    public abstract String getTitleLabel();
}
