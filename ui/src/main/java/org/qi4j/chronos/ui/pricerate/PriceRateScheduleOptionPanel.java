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
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.ui.base.BasePage;

public abstract class PriceRateScheduleOptionPanel extends Panel
{
    private SubmitLink newPriceRateScheduleLink;
    private SubmitLink customizePriceRateScheduleLink;

    private Label priceRateScheduleNameLabel;

    //TODO bp. Remove priceRateSchedule
    private static PriceRateScheduleComposite priceRateSchedule;

    public PriceRateScheduleOptionPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        //TODO bp. set it to null due to it is a static variable
        priceRateSchedule = null;

        priceRateScheduleNameLabel = new Label( "priceRateScheduleNameLabel", "" );

        newPriceRateScheduleLink = new SubmitLink( "newPriceRateScheduleLink" )
        {
            public void onSubmit()
            {
                handleNewPriceRateSchedule();
            }
        };

        customizePriceRateScheduleLink = new SubmitLink( "customizePriceRateScheduleLink" )
        {
            public void onSubmit()
            {
                handleCustomizePriceRateSchedule();
            }
        };

        priceRateScheduleNameLabel.setVisible( false );
        customizePriceRateScheduleLink.setVisible( false );

        add( priceRateScheduleNameLabel );
        add( newPriceRateScheduleLink );
        add( customizePriceRateScheduleLink );
    }

    private void handleViewPriceRateSchedule()
    {
        PriceRateScheduleDetailPage detailPage = new PriceRateScheduleDetailPage( (BasePage) this.getPage() )
        {
            public PriceRateSchedule getPriceRateSchedule()
            {
                return PriceRateScheduleOptionPanel.this.getPriceRateSchedule();
            }
        };

        setResponsePage( detailPage );
    }

    private void handleCustomizePriceRateSchedule()
    {
        PriceRateScheduleEditPage editPage = new PriceRateScheduleEditPage( (BasePage) this.getPage() )
        {
            public PriceRateScheduleComposite getPriceRateSchedule()
            {
                return PriceRateScheduleOptionPanel.this.getPriceRateSchedule();
            }

            public void updatePriceRateSchedule( PriceRateScheduleComposite priceRateScheduleComposite )
            {
                setPriceRateSchedule( priceRateScheduleComposite );
            }
        };

        setResponsePage( editPage );
    }

    public PriceRateScheduleComposite getPriceRateSchedule()
    {
        return priceRateSchedule;
    }

    private void handleNewPriceRateSchedule()
    {
        PriceRateScheduleAddPage addPage = new PriceRateScheduleAddPage( (BasePage) this.getPage() )
        {
            public void addPriceRateSchedule( PriceRateScheduleComposite priceRateScheduleComposite )
            {
                PriceRateScheduleOptionPanel.this.addPriceRateSchedule( priceRateScheduleComposite );
            }
        };

        setResponsePage( addPage );
    }

    private void addPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule )
    {
        setPriceRateSchedule( priceRateSchedule );
    }

    public boolean checkIfNotValidated()
    {
        if( priceRateSchedule == null )
        {
            error( "Please create a new price Rate Schedule." );
            return true;
        }

        return false;
    }

    public void setPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule )
    {
        this.priceRateSchedule = priceRateSchedule;

        priceRateScheduleNameLabel.setModelObject( priceRateSchedule.getName() );

        priceRateScheduleNameLabel.setVisible( true );
        customizePriceRateScheduleLink.setVisible( true );
    }

    public abstract AccountEntityComposite getAccount();
}
