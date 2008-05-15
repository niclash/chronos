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
package org.qi4j.chronos.ui.pricerate;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.composites.PriceRateScheduleEntityComposite;
import org.qi4j.chronos.ui.common.model.NameModel;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;

public class PriceRateScheduleDetailPage extends LeftMenuNavPage
{
    private Page returnBase;

    public PriceRateScheduleDetailPage( Page returnBase, final String priceRateScheduleId )
    {
        this.returnBase = returnBase;

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return getUnitOfWork().find( priceRateScheduleId, PriceRateScheduleEntityComposite.class );
                    }
                }
            )
        );
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new PriceRateScheduleDetailForm( "priceRateScheduleDetailForm", getModel() ) );
    }

    private class PriceRateScheduleDetailForm extends Form
    {
        private TextField nameField;
        private TextField currencyField;
        private Button submitButton;
        private PriceRateListView priceRateListView;

        public PriceRateScheduleDetailForm( final String id, final IModel iModel )
        {
            super( id );

            initComponents( iModel );
        }

        private void initComponents( IModel iModel )
        {
            final PriceRateSchedule priceRateSchedule = (PriceRateSchedule) iModel.getObject();
            priceRateListView = new PriceRateListView( "priceRateListView", iModel );
            currencyField =
                new TextField( "currencyField", new Model( priceRateSchedule.currency().get().getCurrencyCode() ) );
            nameField = new TextField( "nameField", new NameModel( iModel ) );
            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnBase );
                }
            };

            add( currencyField );
            add( nameField );
            add( submitButton );
            add( priceRateListView );
        }
    }
}
