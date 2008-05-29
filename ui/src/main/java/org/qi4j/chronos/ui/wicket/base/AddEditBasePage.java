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
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.library.framework.validation.ValidationMessage;

public abstract class AddEditBasePage<T> extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private Page goBackPage;

    public AddEditBasePage( final Page goBackPage, IModel<T> model )
    {
        this.goBackPage = goBackPage;

        ChronosUnitOfWorkManager.get().setPolicy( ChronosUnitOfWorkManager.Policy.RESET_ON_END_REQUEST );

        Form<T>  addEditForm = new AddEditForm( "addEditForm", model );
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( addEditForm );

        String titleLabel = getTitleLabel();
        add( new Label( "titleLabel", titleLabel ) );

        initComponent( addEditForm );
    }

    private class AddEditForm extends Form<T>
    {
        private static final long serialVersionUID = 1L;

        public AddEditForm( String id, IModel<T> model )
        {
            super( id, new ChronosCompoundPropertyModel<T>( model.getObject() ) );

            String buttonValue = getSubmitButtonValue();

            Button<String> submitButton =
                new Button<String>( "submitButton", new Model<String>( buttonValue ) )
                {
                    private static final long serialVersionUID = 1L;

                    public void onSubmit()
                    {
                        handleSubmitClicked( AddEditForm.this.getModel() );
                    }
                };

            Button<String> cancelButton =
                new Button<String>( "cancelButton", new Model<String>( "Cancel" ) )
                {
                    private static final long serialVersionUID = 1L;

                    public void onSubmit()
                    {
                        handleCancelClicked();
                    }
                };

            cancelButton.setDefaultFormProcessing( false );

            add( submitButton );
            add( cancelButton );
        }
    }

    protected void handleCancelClicked()
    {
        divertToGoBackPage();
    }

    protected void divertToGoBackPage()
    {
        setResponsePage( goBackPage );
    }

    protected final Page getGoBackPage()
    {
        return goBackPage;
    }

    protected final void logInfoMsg( String msg )
    {
        goBackPage.info( msg );
    }

    protected final void logErrorMsg( String msg )
    {
        goBackPage.error( msg );
    }

    protected final void logErrorMsg( List<ValidationMessage> msgs )
    {
        for( ValidationMessage msg : msgs )
        {
            logErrorMsg( msg.getMessage() );
        }
    }

    protected abstract void initComponent( Form<T> form );

    protected abstract void handleSubmitClicked( IModel<T> model );

    protected abstract String getSubmitButtonValue();

    protected abstract String getTitleLabel();
}
