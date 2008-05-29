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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.PriceRateSchedule;

public abstract class PriceRateOptionPanel extends Panel
{
    private SubmitLink newPriceRateLink;
    private SubmitLink customizePriceRateLink;

    //TODO bp. fix this
    private static PriceRate priceRate;

    private Label priceRateDetailLabel;

    public PriceRateOptionPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        priceRateDetailLabel = new Label( "priceRateDetailLabel", "" );

        newPriceRateLink = new SubmitLink( "newPriceRateLink" )
        {
            public void onSubmit()
            {
                handleNewPriceRate();
            }
        };

        customizePriceRateLink = new SubmitLink( "customizePriceRateLink" )
        {
            public void onSubmit()
            {
                handleCustomizePriceRate();
            }
        };

        priceRateDetailLabel.setVisible( false );
        customizePriceRateLink.setVisible( false );

        add( priceRateDetailLabel );
        add( newPriceRateLink );
        add( customizePriceRateLink );
    }

    private void handleNewPriceRate()
    {
/*
        PriceRateAddPage addPage = new PriceRateAddPage( (BasePage) this.getPage() )
        {
            public void addPriceRate( PriceRate priceRate )
            {
                PriceRateOptionPanel.this.addPriceRate( priceRate );
            }

            public PriceRateSchedule getPriceRateSchedule()
            {
                return PriceRateOptionPanel.this.getPriceRateSchedule();
            }

            public String getSubmitButtonValue()
            {
                return "Done";
            }
        };

        setResponsePage( addPage );
*/
    }

    private void addPriceRate( PriceRate priceRate )
    {
        setPriceRate( priceRate );
    }

    private void handleCustomizePriceRate()
    {
/*
        PriceRateEditPage editPage = new PriceRateEditPage( (BasePage) this.getPage() )
        {
            public PriceRate getPriceRate()
            {
                return PriceRateOptionPanel.this.getPriceRate();
            }

            public void updatePriceRate( PriceRate priceRate )
            {
                PriceRateOptionPanel.this.updatePriceRate( priceRate );
            }

            public PriceRateSchedule getPriceRateSchedule()
            {
                return PriceRateOptionPanel.this.getPriceRateSchedule();
            }
        };

        setResponsePage( editPage );
*/
    }

    private void updatePriceRate( PriceRate priceRate )
    {
        setPriceRate( priceRate );
    }

    public PriceRate getPriceRate()
    {
        return priceRate;
    }

    public boolean checkIfNotValidated()
    {
        if( priceRate == null )
        {
            error( "Please create a new price Rate." );
            return true;
        }

        return false;
    }

    public void setPriceRate( PriceRate priceRate )
    {
        this.priceRate = priceRate;

        StringBuilder builder = new StringBuilder();

        builder.append( priceRate.projectRole().get().name().get() ).append( " - " )
            .append( priceRate.priceRateType().get().toString() )
            .append( " - " )
            .append( String.valueOf( priceRate.amount().get() ) );

        priceRateDetailLabel.setModelObject( builder.toString() );

        priceRateDetailLabel.setVisible( true );
        customizePriceRateLink.setVisible( true );
    }

    public abstract PriceRateSchedule getPriceRateSchedule();
}

