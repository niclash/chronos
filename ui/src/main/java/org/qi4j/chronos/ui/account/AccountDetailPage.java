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
package org.qi4j.chronos.ui.account;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.ui.address.AddressDetailPanel;
import org.qi4j.chronos.ui.address.NameModel;
import org.qi4j.chronos.ui.address.CompositeModel;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosSession;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;

public class AccountDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public AccountDetailPage( @Uses Page returnPage, final @Uses String accountId )
    {
        this.returnPage = returnPage;

        setModel( new CompoundPropertyModel(
            new LoadableDetachableModel()
            {
                public Object load()
                {
                    return getUnitOfWork().find( accountId, AccountEntityComposite.class );
                }
            }
            )
        );

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new AccountDetailForm( "accountDetailForm", getModel() ) );
    }

    /**
     * Query the unit of work factory for existing or new unit of work.
     * TODO kamil: consider retrieving the factory from ChronosWebApp instead of session.
     * @return
     */
    protected UnitOfWork getUnitOfWork()
    {
        UnitOfWorkFactory factory = ChronosSession.get().getUnitOfWorkFactory();

        if( null == factory.currentUnitOfWork() || !factory.currentUnitOfWork().isOpen() )
        {
            return factory.newUnitOfWork();
        }
        else
        {
            return factory.currentUnitOfWork();
        }
    }

    private class AccountDetailForm extends Form
    {
        private Button goButton;
        private TextField nameField;
        private TextField referenceField;
        private AddressDetailPanel addressDetailPanel;


        public AccountDetailForm( String id, IModel iModel )
        {
            super( id );

            nameField = new TextField( "nameField", new NameModel( iModel ) );
            referenceField = new TextField( "referenceField", new CompositeModel( iModel, "reference" ) );

            addressDetailPanel = new AddressDetailPanel( "addressDetailPanel", new CompositeModel( iModel, "address") );

            goButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            add( nameField );
            add( referenceField );
            add( addressDetailPanel );

            add( goButton );
        }
    }

    @Override public boolean isVersioned()
    {
        return false;
    }

    @Override protected void setHeaders( WebResponse response)
    {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, max-age=0, must-revalidate, no-store");
    }
}
